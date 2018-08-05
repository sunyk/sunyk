package com.concurrent.read.impl;

/**
 * created on sunyang 2018/7/5 17:34
 * Are you different!"jia you" for me
 */
public class No5 {

    /**
     * 是什么？
     * Semaphore是计数信号量。Semaphore管理一系列许可证。每个acquire方法阻塞，直到有一个许可证可以获得然后拿走一个许可证；每个release方法增加一个许可证，
     * 这可能会释放一个阻塞的acquire方法。然而，其实并没有实际的许可证这个对象，Semaphore只是维持了一个可获得许可证的数量。 
     * 干什么？
     * Semaphore经常用于限制获取某种资源的线程数量。
     * 源码分析：
     * Semaphore有两种模式，公平模式和非公平模式。公平模式就是调用acquire的顺序就是获取许可证的顺序，遵循FIFO；而非公平模式是抢占式的，
     * 也就是有可能一个新的获取线程恰好在一个许可证释放时得到了这个许可证，而前面还有等待的线程。
     * 构造方法
     * Semaphore有两个构造方法，如下：
     *        public Semaphore(int permits) {
     *         sync = new NonfairSync(permits);
     *     }
     *
     *     public Semaphore(int permits, boolean fair) {
     *         sync = fair ? new FairSync(permits) : new NonfairSync(permits);
     *     }
     * 从上面可以看到两个构造方法，都必须提供许可的数量，第二个构造方法可以指定是公平模式还是非公平模式，默认非公平模式。 
     * Semaphore内部基于AQS的共享模式，所以实现都委托给了Sync类。 
     * 这里就看一下NonfairSync的构造方法：
     *  NonfairSync(int permits) {
     *             super(permits);
     *         }
     * 可以看到直接调用了父类的构造方法，Sync的构造方法如下：
     * Sync(int permits) {
     *             setState(permits);
     *         }
     * 可以看到调用了setState方法，也就是说AQS中的资源就是许可证的数量。
     * 获取许可
     * 先从获取一个许可看起，并且先看非公平模式下的实现。首先看acquire方法，acquire方法有几个重载，但主要是下面这个方法
     * public void acquire(int permits) throws InterruptedException {
     *         if (permits < 0) throw new IllegalArgumentException();
     *         sync.acquireSharedInterruptibly(permits);
     * }
     *
     * 从上面可以看到，调用了Sync的acquireSharedInterruptibly方法，该方法在父类AQS中，如下：
     * public final void acquireSharedInterruptibly(int arg)
     *             throws InterruptedException {
     *         //如果线程被中断了，抛出异常
     *         if (Thread.interrupted())
     *             throw new InterruptedException();
     *         //获取许可失败，将线程加入到等待队列中
     *         if (tryAcquireShared(arg) < 0)
     *             doAcquireSharedInterruptibly(arg);
     *     }
     * AQS子类如果要使用共享模式的话，需要实现tryAcquireShared方法，下面看NonfairSync的该方法实现：
     *  protected int tryAcquireShared(int acquires) {
     *             return nonfairTryAcquireShared(acquires);
     *         }
     * 该方法调用了父类中的nonfairTyAcquireShared方法，如下：
     * final int nonfairTryAcquireShared(int acquires) {
     *             for (;;) {
     *                 //获取剩余许可数量
     *                 int available = getState();
     *                 //计算给完这次许可数量后的个数
     *                 int remaining = available - acquires;
     *                 //如果许可不够或者可以将许可数量重置的话，返回
     *                 if (remaining < 0 ||
     *                     compareAndSetState(available, remaining))
     *                     return remaining;
     *             }
     *         }
     * 从上面可以看到，只有在许可不够时返回值才会小于0，其余返回的都是剩余许可数量，这也就解释了，一旦许可不够，后面的线程将会阻塞。
     * 看完了非公平的获取，再看下公平的获取，代码如下：
     *  protected int tryAcquireShared(int acquires) {
     *             for (;;) {
     *                 //如果前面有线程再等待，直接返回-1
     *                 if (hasQueuedPredecessors())
     *                     return -1;
     *                 //后面与非公平一样
     *                 int available = getState();
     *                 int remaining = available - acquires;
     *                 if (remaining < 0 ||
     *                     compareAndSetState(available, remaining))
     *                     return remaining;
     *             }
     *         }
     *
     * 从上面可以看到，FairSync与NonFairSync的区别就在于会首先判断当前队列中有没有线程在等待，如果有，就老老实实进入到等待队列；
     * 而不像NonfairSync一样首先试一把，说不定就恰好获得了一个许可，这样就可以插队了。 
     * 看完了获取许可后，再看一下释放许可。
     * 释放许可
     * 释放许可也有几个重载方法，但都会调用下面这个带参数的方法，
     * public void release(int permits) {
     *         if (permits < 0) throw new IllegalArgumentException();
     *         sync.releaseShared(permits);
     *     }
     * releaseShared方法在AQS中，如下：
     * public final boolean releaseShared(int arg) {
     *         //如果改变许可数量成功
     *         if (tryReleaseShared(arg)) {
     *             doReleaseShared();
     *             return true;
     *         }
     *         return false;
     *     }
     * AQS子类实现共享模式的类需要实现tryReleaseShared类来判断是否释放成功，实现如下：
     * protected final boolean tryReleaseShared(int releases) {
     *             for (;;) {
     *                 //获取当前许可数量
     *                 int current = getState();
     *                 //计算回收后的数量
     *                 int next = current + releases;
     *                 if (next < current) // overflow
     *                     throw new Error("Maximum permit count exceeded");
     *                 //CAS改变许可数量成功，返回true
     *                 if (compareAndSetState(current, next))
     *                     return true;
     *             }
     *         }
     * 从上面可以看到，一旦CAS改变许可数量成功，那么就会调用doReleaseShared()方法释放阻塞的线程。
     * 减小许可数量
     * Semaphore还有减小许可数量的方法，该方法可以用于用于当资源用完不能再用时，这时就可以减小许可证。代码如下：
     * protected void reducePermits(int reduction) {
     *         if (reduction < 0) throw new IllegalArgumentException();
     *         sync.reducePermits(reduction);
     *     }
     * 可以看到，委托给了Sync，Sync的reducePermits方法如下：
     *   final void reducePermits(int reductions) {
     *             for (;;) {
     *                 //得到当前剩余许可数量
     *                 int current = getState();
     *                 //得到减完之后的许可数量
     *                 int next = current - reductions;
     *                 if (next > current) // underflow
     *                     throw new Error("Permit count underflow");
     *                 //如果CAS改变成功
     *                 if (compareAndSetState(current, next))
     *                     return;
     *             }
     *         }
     * 从上面可以看到，就是CAS改变AQS中的state变量，因为该变量代表许可证的数量。
     * 获取剩余许可数量　　
     * Semaphore还可以一次将剩余的许可数量全部取走，该方法是drain方法，如下：
     * public int drainPermits() {
     *         return sync.drainPermits();
     *     }
     * Sync的实现如下：
     *  final int drainPermits() {
     *             for (;;) {
     *                 int current = getState();
     *                 if (current == 0 || compareAndSetState(current, 0))
     *                     return current;
     *             }
     *         }
     * 可以看到，就是CAS将许可数量置为0。
     *
     * 总结
     * Semaphore是信号量，用于管理一组资源。
     * 其内部是基于AQS的共享模式，AQS的状态表示许可证的数量，在许可证数量不够时，线程将会被挂起；
     * 而一旦有一个线程释放一个资源，那么就有可能重新唤醒等待队列中的线程继续执行。
     */
}
