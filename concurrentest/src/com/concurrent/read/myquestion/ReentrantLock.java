package com.concurrent.read.myquestion;

/**
 * created on sunyang 2018/7/20 11:11
 * Are you different!"jia you" for me
 */
public class ReentrantLock {
    /*
        Lock接口
        重入锁ReetrantLock
        并发基础组件AQS与ReetrantLock
        AQS工作原理概要
        基于ReetrantLock分析AQS独占模式实现过程
        ReetrantLock中非公平锁
        ReetrantLock中公平锁
        关于synchronized 与ReentrantLock
        神奇的Condition
        关于Condition接口
        Condition的使用案例-生产者消费者模式
        Condition的实现原理
     */

    /**
     * Lock接口  （1.5引入）
     * 在Java 1.5中，官方在concurrent并发包中加入了Lock接口，该接口中提供了lock()方法和unLock()方法对显式加锁和显式释放锁操作进行支持。
     * 重入锁ReetrantLock
     * 重入锁ReetrantLock，JDK 1.5新增的类，实现了Lock接口，作用与synchronized关键字相当，但比synchronized更加灵活。
     * ReetrantLock本身也是一种支持重进入的锁，即该锁可以支持一个线程对资源重复加锁，同时也支持公平锁与非公平锁。
     * 所谓的公平与非公平指的是在请求先后顺序上，先对锁进行请求的就一定先获取到锁，那么这就是公平锁，
     * 反之，如果对于锁的获取并没有时间上的先后顺序，如后请求的线程可能先获取到锁，这就是非公平锁
     * 一般而言非，非公平锁机制的效率往往会胜过公平锁的机制，但在某些场景下，可能更注重时间先后顺序，
     * 那么公平锁自然是很好的选择。需要注意的是ReetrantLock支持对同一线程重加锁，但是加锁多少次，就必须解锁多少次，这样才可以成功释放锁。
     * 并发基础组件AQS与ReetrantLock
     * AQS工作原理概要
     * AbstractQueuedSynchrionzer又称为队列同步器，他是用来构建锁或其他同步组件的基础框架，内部通过一个int类型的成员变量state来控制同步状态，当state=0时，则说明没有任何线程
     * 占有共享资源的锁，当state=1时，则说明有线程目前正在使用共享变量，其他线程必须加入同步队列等待，AQS内部通过内部类Node构成IFIO的同步队列来完成线程获取的锁的排队工作，
     * 同时利用内部类ConditionObject构建等待队列，当Condition调用wait()方法后，线程将会加入等待队列中，而当Condition调用signal()方法后，
     * 线程将从等待队列转移动同步队列中进行锁竞争。注意这里涉及到两种队列，
     * 一种的同步队列，当线程请求锁而等待的后将加入同步队列等待，而另一种则是等待队列(可有多个)，通过Condition调用await()方法释放锁后，将加入等待队列。
     * 关于Condition的等待队列我们后面再分析，这里我们先来看看AQS中的同步队列模型
     * 中SHARED和EXCLUSIVE常量分别代表共享模式和独占模式，所谓共享模式是一个锁允许多条线程同时操作，如信号量Semaphore采用的就是基于AQS的共享模式实现的，
     * 而独占模式则是同一个时间段只能有一个线程对共享资源进行操作，多余的请求线程需要排队等待，如ReentranLock。
     * 变量waitStatus则表示当前被封装成Node结点的等待状态，共有4种取值CANCELLED（1）、SIGNAL（-1）、CONDITION（-2）、PROPAGATE（-3）。
     * CANCELLED：值为1，在同步队列中等待的线程等待超时或被中断，需要从同步队列中取消该Node的结点，其结点的waitStatus为CANCELLED，即结束状态，进入该状态后的结点将不会再变化。
     *
     * SIGNAL：值为-1，被标识为该等待唤醒状态的后继结点，当其前继结点的线程释放了同步锁或被取消，将会通知该后继结点的线程执行。说白了，就是处于唤醒状态，只要前继结点释放锁，就会通知标识为SIGNAL状态的后继结点的线程执行。
     *
     * CONDITION：值为-2，与Condition相关，该标识的结点处于等待队列中，结点的线程等待在Condition上，当其他线程调用了Condition的signal()方法后，CONDITION状态的结点将从等待队列转移到同步队列中，等待获取同步锁。
     *
     * PROPAGATE：值为-3，与共享模式相关，在共享模式中，该状态标识结点的线程处于可运行状态。
     *
     * 0状态：值为0，代表初始化状态。
     * 对于锁的实现存在两种不同的模式，即共享模式(如Semaphore)和独占模式(如ReetrantLock)，无论是共享模式还是独占模式的实现类，其内部都是基于AQS实现的，
     * 也都维持着一个虚拟的同步队列，当请求锁的线程超过现有模式的限制时，会将线程包装成Node结点并将线程当前必要的信息存储到node结点中，然后加入同步队列等会获取锁，
     * 而这系列操作都有AQS协助我们完成，这也是作为基础组件的原因，无论是Semaphore还是ReetrantLock，其内部绝大多数方法都是间接调用AQS完成的。
     * AbstractOwnableSynchronizer：抽象类，定义了存储独占当前锁的线程和获取的方法
     *
     * AbstractQueuedSynchronizer：抽象类，AQS框架核心类，其内部以虚拟队列的方式管理线程的锁获取与锁释放，其中获取锁(tryAcquire方法)和释放锁(tryRelease方法)并没有提供默认实现，需要子类重写这两个方法实现具体逻辑，目的是使开发人员可以自由定义获取锁以及释放锁的方式。
     *
     * Node：AbstractQueuedSynchronizer 的内部类，用于构建虚拟队列(链表双向链表)，管理需要获取锁的线程。
     *
     * Sync：抽象类，是ReentrantLock的内部类，继承自AbstractQueuedSynchronizer，实现了释放锁的操作(tryRelease()方法)，并提供了lock抽象方法，由其子类实现。
     *
     * NonfairSync：是ReentrantLock的内部类，继承自Sync，非公平锁的实现类。
     *
     * FairSync：是ReentrantLock的内部类，继承自Sync，公平锁的实现类。
     *
     * ReentrantLock：实现了Lock接口的，其内部类有Sync、NonfairSync、FairSync，在创建时可以根据fair参数决定创建NonfairSync(默认非公平锁)还是FairSync。
     *
     * 基于ReetrantLock分析AQS独占模式实现过程
     * ReetrantLock中非公平锁
     * AQS同步器的实现依赖于内部的同步队列(FIFO的双向链表对列)完成对同步状态(state)的管理，当前线程获取锁(同步状态)失败时，AQS会将该线程以及相关等待信息包装成一个节点(Node)
     * 并将其加入同步队列，同时会阻塞当前线程，当同步状态释放时，会将头结点head中的线程唤醒，让其尝试获取同步状态。关于同步队列和Node结点，前面我们已进行了较为详细的分析，
     * 这里重点分析一下获取同步状态和释放同步状态以及如何加入队列的具体操作，这里从ReetrantLock入手分析AQS的具体实现，我们先以非公平锁为例进行分析。
     * //默认构造，创建非公平锁NonfairSync
     * public ReentrantLock() {
     *     sync = new NonfairSync();
     * }
     * //根据传入参数创建锁类型
     * public ReentrantLock(boolean fair) {
     *     sync = fair ? new FairSync() : new NonfairSync();
     * }
     *
     * //加锁操作
     * public void lock() {
     *      sync.lock();
     * }
     *
     * 非公平锁实现
     * static final class NonfairSync extends Sync {
     *     //加锁
     *     final void lock() {
     *         //执行CAS操作，获取同步状态
     *         if (compareAndSetState(0, 1))
     *        //成功则将独占锁线程设置为当前线程
     *           setExclusiveOwnerThread(Thread.currentThread());
     *         else
     *             //否则再次请求同步状态
     *             acquire(1);
     *     }
     * }
     *这里获取锁时，首先对同步状态执行CAS操作，尝试把state的状态从0设置为1，如果返回true则代表获取同步状态成功，也就是当前线程获取锁成，可操作临界资源，
     * 果返回false，则表示已有线程持有该同步状态(其值为1)，获取锁失败，注意这里存在并发的情景，也就是可能同时存在多个线程设置state变量，
     * 因此是CAS操作保证了state变量操作的原子性。返回false后，执行 acquire(1)方法
     * 该方法是AQS中的方法，它对中断不敏感，即使线程获取同步状态失败，进入同步队列，后续对该线程执行中断操作也不会从同步队列中移出，方法如下
     * public final void acquire(int arg) {
     *     //再次尝试获取同步状态
     *     if (!tryAcquire(arg) &&
     *         acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
     *         selfInterrupt();
     * }
     * 这里传入参数arg表示要获取同步状态后设置的值(即要设置state的值)，因为要获取锁，而status为0时是释放锁，1则是获取锁，
     * 所以这里一般传递参数为1，进入方法后首先会执行tryAcquire(arg)方法
     * ReetrantLock中公平锁
     * 在获取锁的时，公平锁的获取顺序是完全遵循时间上的FIFO规则，也就是说先请求的线程一定会先获取锁，后来的线程肯定需要排队，
     * 这点与前面我们分析非公平锁的nonfairTryAcquire(int acquires)方法实现有锁不同，下面是公平锁中tryAcquire()方法的实现
     * //公平锁FairSync类中的实现
     * protected final boolean tryAcquire(int acquires) {
     *             final Thread current = Thread.currentThread();
     *             int c = getState();
     *             if (c == 0) {
     *             //注意！！这里先判断同步队列是否存在结点
     *                 if (!hasQueuedPredecessors() &&
     *                     compareAndSetState(0, acquires)) {
     *                     setExclusiveOwnerThread(current);
     *                     return true;
     *                 }
     *             }
     *             else if (current == getExclusiveOwnerThread()) {
     *                 int nextc = c + acquires;
     *                 if (nextc < 0)
     *                     throw new Error("Maximum lock count exceeded");
     *                 setState(nextc);
     *                 return true;
     *             }
     *             return false;
     *         }
     *
     * ReentrantLock的内部实现原理
     * 这里我们简单进行小结，重入锁ReentrantLock，是一个基于AQS并发框架的并发控制类，其内部实现了3个类，分别是Sync、NoFairSync以及FairSync类，其中Sync继承自AQS，
     * 实现了释放锁的模板方法tryRelease(int)，而NoFairSync和FairSync都继承自Sync，实现各种获取锁的方法tryAcquire(int)。
     * ReentrantLock的所有方法实现几乎都间接调用了这3个类，因此当我们在使用ReentrantLock时，大部分使用都是在间接调用AQS同步器中的方法，这就是ReentrantLock的内部实现原理
     *
     * 关于synchronized 与ReentrantLock
     * 在JDK 1.6之后，虚拟机对于synchronized关键字进行整体优化后，在性能上synchronized与ReentrantLock已没有明显差距，因此在使用选择上，
     * 需要根据场景而定，大部分情况下我们依然建议是synchronized关键字，原因之一是使用方便语义清晰，二是性能上虚拟机已为我们自动优化。
     * 而ReentrantLock提供了多样化的同步特性，如超时获取锁、可以被中断获取锁（synchronized的同步是不能中断的）、等待唤醒机制的多个条件变量(Condition)等，
     * 因此当我们确实需要使用到这些功能是，可以选择ReentrantLock.
     *
     * 神奇的Condition
     * 关于Condition接口
     * 在并发编程中，每个Java对象都存在一组监视器方法，如wait()、notify()以及notifyAll()方法，通过这些方法，我们可以实现线程间通信与协作（也称为等待唤醒机制），
     * 如生产者-消费者模式，而且这些方法必须配合着synchronized关键字使用，与synchronized的等待唤醒机制相比Condition具有更多的灵活性以及精确性，
     * 这是因为notify()在唤醒线程时是随机(同一个锁)，而Condition则可通过多个Condition实例对象建立更加精细的线程控制，也就带来了更多灵活性了，我们可以简单理解为以下两点
     * 1.通过Condition能够精细的控制多线程的休眠与唤醒。
     *
     * 2.对于一个锁，我们可以为多个线程间建立不同的Condition。
     * 关于Condition的实现类是AQS的内部类ConditionObject
     * Condition的实现原理
     * Condition的具体实现类是AQS的内部类ConditionObject，前面我们分析过AQS中存在两种队列，一种是同步队列，一种是等待队列，而等待队列就相对于Condition而言的。
     * 注意在使用Condition前必须获得锁，同时在Condition的等待队列上的结点与前面同步队列的结点是同一个类即Node，其结点的waitStatus的值为CONDITION。
     * 在实现类ConditionObject中有两个结点分别是firstWaiter和lastWaiter，firstWaiter代表等待队列第一个等待结点，lastWaiter代表等待队列最后一个等待结点。
     * 每个Condition都对应着一个等待队列，也就是说如果一个锁上创建了多个Condition对象，那么也就存在多个等待队列。等待队列是一个FIFO的队列，
     * 在队列中每一个节点都包含了一个线程的引用，而该线程就是Condition对象上等待的线程。当一个线程调用了await()相关的方法，那么该线程将会释放锁，
     * 并构建一个Node节点封装当前线程的相关信息加入到等待队列中进行等待，直到被唤醒、中断、超时才从队列中移出。
     *
     * await()方法主要做了3件事，一是调用addConditionWaiter()方法将当前线程封装成node结点加入等待队列，二是调用fullyRelease(node)方法释放同步状态并唤醒后继结点的线程。
     * 三是调用isOnSyncQueue(node)方法判断结点是否在同步队列中，注意是个while循环，如果同步队列中没有该结点就直接挂起该线程，
     * 需要明白的是如果线程被唤醒后就调用acquireQueued(node, savedState)执行自旋操作争取锁，即当前线程结点从等待队列转移到同步队列并开始努力获取锁。
     * signal()方法做了两件事，一是判断当前线程是否持有独占锁，没有就抛出异常，从这点也可以看出只有独占模式先采用等待队列，而共享模式下是没有等待队列的，
     * 也就没法使用Condition。二是唤醒等待队列的第一个结点，即执行doSignal(first)
     * doSignal(first)方法中做了两件事，从条件等待队列移除被唤醒的节点，然后重新维护条件等待队列的firstWaiter和lastWaiter的指向。
     * 二是将从等待队列移除的结点加入同步队列(在transferForSignal()方法中完成的)，如果进入到同步队列失败并且条件等待队列还有不为空的节点，则继续循环唤醒后续其他结点的线程。
     * 等待唤醒机制的整个流程实现原理
     * 即signal()被调用后，先判断当前线程是否持有独占锁，如果有，那么唤醒当前Condition对象中等待队列的第一个结点的线程，并从等待队列中移除该结点，移动到同步队列中，
     * 如果加入同步队列失败，那么继续循环唤醒等待队列中的其他结点的线程，如果成功加入同步队列，那么如果其前驱结点是否已结束或者设置前驱节点状态为Node.SIGNAL状态失败，
     * 则通过LockSupport.unpark()唤醒被通知节点代表的线程，到此signal()任务完成，注意被唤醒后的线程，将从前面的await()方法中的while循环中退出，
     * 因为此时该线程的结点已在同步队列中，那么while (!isOnSyncQueue(node))将不在符合循环条件，进而调用AQS的acquireQueued()方法加入获取同步状态的竞争中，
     * 这就是等待唤醒机制的整个流程实现原理（注意无论是同步队列还是等待队列使用的Node数据结构都是同一个，不过是使用的内部变量不同罢了）
     *
     *
     *
     */

}
