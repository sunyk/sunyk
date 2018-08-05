package com.concurrent.read.myquestion;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * created on sunyang 2018/7/17 10:39
 * Are you different!"jia you" for me
 */
public class SyncAndLock {
    /**
     *
     * synchronized的三种应用方式
     * 1. synchronized作用于实例方法
     * 2.synchronized作用于静态方法
     * 3.synchronized同步代码块
     * synchronized底层语义原理
     * 理解Java对象头与Monitor
     * synchronized代码块底层原理
     * synchronized方法底层原理
     * Java虚拟机对synchronized的优化
     * 偏向锁
     * 轻量级锁
     * 自旋锁
     * 锁消除
     * 关于synchronized 可能需要了解的关键点
     * synchronized的可重入性
     * 线程中断与synchronized
     * 线程中断
     * 中断与synchronized
     * 等待唤醒机制与synchronized
     *
     *  实现原理：java虚拟机中的同步synchronize基于进入和退出Monitor对象来实现，无论是显式同步，还是隐式同步都是如此。在java语言中，同步用最多的地方可能是被synchronized修饰的
     *  同步方法。同步方法并不是由monitorenter和monitorexit指令来实现同步的，而是由方法调用指令读取运行时常量池中方法的acc_synchronized标志来隐式实现的。
     *  显示同步：有明确的monitorenter和monitorexit指令，即为同步代码块。
     *  一般而言，synchronized使用的锁对象是存储在java对象头里的，jvm中采用2个字来存储对象头（如果对象是数组则会分配3个字，多出来的1个字记录的是数组长度），其主要结构是由
     *  Mark Word和Class Metadata Address组成。
     *  Mark Word:存储对象的hashcode、锁信息或分代年龄或GC标志等信息
     *  Class Metadata Address:类型指针向对象的类元数据，JVM通过这个指针确定该对象是哪个类的实例。
     *
     * Java6对synchronized锁进行了优化，新加了轻量级锁和偏向锁。
     * 重量级锁也就是通常说synchronized的对象锁，锁标识位为10，其中指针指向的是monitor对象的起始地址。
     * monitor：是由ObjectMonitor实现的
     * monitor对象存在于每个Java对象的对象头中（存储的指针的指向），synchronized锁便是通过这种方式获取锁的。
     * 为什么Java中任意对象可以作为锁？同时notify/notifyAll/wait等方法为什么存在于顶级对象Object中？
     * 都是基于synchronized在字节码层门的具体语义实现，synchronized代码块底层原理
     * 从字节码中可知同步语句块的实现使用的是monitorenter和monitorexit指令，其中monitorenter指令指向同步代码块的开始位置，monitorexit指令则指明同步代码块的结束位置。
     * 当执行monitorenter指令时，当前线程将试图获取objectref对象锁所在对应的monitor的持有权，当objectref的monitor的进入计数器为0.那线程可以成功取得monitor，并将计数器
     * 设置为1，获取锁成功。
     * 如果当前线程已经拥有objectref的monitor的持有权，那它可以重入这个monitor，重入时计数器的值也会加1。倘若其他线程已经拥有objectref的monitor的所有权，那当前线程将被
     * 阻塞，直到正在执行线程执行完毕，即monitorexit指令被执行，执行线程将释放monitor并设置计数器值为0，其他线程将有机会持有monitor。
     * synchronized方法底层原理
     * 方法级的同步是隐式，即无需通过字节码指令来控制的，它实现在方法调用和返回操作中。JVM可以从方法常量池中的方法表结构中的acc_synchronized访问标志区分一个方法是否同步方法。
     * 当方法调用时，调用指令将会检查方法的acc_synchronized访问标志是否被设置，如果设置了，执行线程将先持有monitor，然后再执行方法，最后再方法完成时释放monitor。
     * 在方法执行期间，执行线程持有了monitor，其他任何线程都无法获得同一个monitor。如果一个同步方法执行期间抛出了异常，并且在方法内部无法处理此异常，那这个同步方法所持有的
     * monitor将在异常抛到同步方法之外时自动释放。
     *
     * JVM通过ACC_SYNCHRONIED访问标志来辨别一个方法是否声明为同步方法。
     *
     * JVM对synchronized的优化
     * 锁的状态一共有四种，无锁状态、偏向锁、轻量级锁和重量级锁。随着锁的竞争，锁可以从偏向锁升级到轻量级锁，再升级的重量级锁，锁的升级是单向，从低到高。
     *
     * 偏向锁：
     * 是Java6之后加入的新锁，它是一种针对加锁操作的优化手段，在大多数情况下，锁不仅不存在多线程竞争，而且总是由同一线程多次获得，因此为了减少同一线程获取锁的代价而引入
     * 偏向锁。偏向锁的核心思想是，如果一个线程获得了锁，那么锁就进入偏向模式，此时Mark Word的结构也变为偏向锁结构，当这个线程再次请求锁时，无需在做任何同步操作，即获取锁的过程，
     * 这样就省去了大量有关锁申请的操作，从而也就提供程序的性能。所以，对于没有锁竞争的场合，偏向锁有很好的优化效果，毕竟极有可能连续多次是同一个线程申请相同的锁。但是对于锁竞争
     * 比较激烈的场合，偏向锁就失效了，因为这样场合极有可能每次申请锁的线程都是不相同的，因此这种场合下不应该使用偏向锁，否则会得不偿失，需要注意的是，偏向锁失败后，并不会立即膨胀为
     * 重量级锁，而是先升级为轻量级锁。
     * 轻量级锁：倘若偏向锁失败，虚拟机并不会立即升级为重量级锁，它还会尝试使用一种称为轻量级锁的优化手段（1.6引入的），此时Mark Word的结构也变为轻量级锁的结构。
     * 轻量级锁能够提升程序性能的依据是，对绝大部分的锁，在整个同步周期内都不存在竞争，轻量级锁所适应的场景是线程交替执行同步块的场合，如果存在同一时间访问同一锁的场合，
     * 就会导致轻量级锁膨胀为重量级锁。
     * 自旋锁：
     * 轻量级锁失败后，虚拟机为避免线程真实地在操作系统层面挂起，还会进行一项称为自旋锁的优化手段。这是基于在大多数情况下，线程持有锁的时间都不会太长，如果直接挂起操作系统层面
     * 的线程可能会得不偿失，毕竟操作系统实现线程之间的切换需要从用户态转换到核心态，这个状态之间的转换需要相对较长的时间，时间成本相对较高，因此自旋锁会假设在不久将来，当前的
     * 线程可以获得锁，因为虚拟机会让当前想要获取锁的线程做几个空循环（这也是称为自旋的原因），一般不会太久，可能是50个循环或100循环，在经过若干次循环后，如果得到锁，就顺利进入
     * 临界区。如果还不能获得锁，那就会将线程在操作系统层面挂起，这就是自旋锁的优化方式，这种方式确实也是可以提升效率的。最后没办法也就只能升级为重量级锁了。
     * 锁消除：
     * 消除锁是虚拟机另外一种锁的优化，这种优化更彻底，Java虚拟机在JIT编译时（可以简单理解为当某段代码即将第一次被执行时进行编译，又称即时编译），通过对运行上下文的扫描，去除
     * 不可能存在共享资源竞争的锁，通过这种方式消除没有必要的锁，可以节省毫无意义的请求锁时间，如下stringbuffer的append是一个同步方法，但是在add方法中的stringbuffer属于一个局部变量，
     * 并且不会被其他线程所使用，因此stringbuffer不可能存在共享资源竞争的情景，jvm会自动将其锁消除。
     *
     * 、
     * synchronized的可重入性
     * 从互斥锁的设计上来看，当一个线程视图操作一个由其他线程持有的对象锁的临界资源时，将会处于阻塞状态，但当一个线程再次请求自己持有对象锁的临界资源时，这种情况属于重入锁，
     * 请求将会成功，在java中synchronized是基于原子性的内部锁机制，是可重入的，因此在一个线程调用synchronized方法的同时在其方法体内部调用该对象另外一个synchronized方法，
     * 也就是说一个线程得到一个对象锁后再次请求对象锁，是允许的，这就是synchronized的可重入性。注意由于synchronized是基于monitor实现的，因此每次重入，monitor的计数器仍会加1
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * 两者区别：
     * 1.首先synchronized是java内置关键字，在jvm层面，Lock是个java类；
     *
     * 2.synchronized无法判断是否获取锁的状态，Lock可以判断是否获取到锁；
     *
     * 3.synchronized会自动释放锁(a 线程执行完同步代码会释放锁 ；b 线程执行过程中发生异常会释放锁)，Lock需在finally中手工释放锁（unlock()方法释放锁），否则容易造成线程死锁；
     *
     * 4.用synchronized关键字的两个线程1和线程2，如果当前线程1获得锁，线程2线程等待。
     * 如果线程1阻塞，线程2则会一直等待下去，而Lock锁就不一定会等待下去，如果尝试获取不到锁，线程可以不用一直等待就结束了；
     *
     * 5.synchronized的锁可重入、不可中断、非公平，而Lock锁可重入、可判断、可公平（两者皆可）
     *
     * 6.Lock锁适合大量同步的代码的同步问题，synchronized锁适合代码少量的同步问题。
     */

    private Lock lock = new ReentrantLock();
    public void useLock(Thread thread){
        lock.lock();
        try {
            System.out.println(thread.getName() + "获取当前锁");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            System.out.println(thread.getName() + "发送了异常释放锁");
        } finally {
            System.out.println(thread.getName() + "执行完毕释放锁");
            lock.unlock();
        }
    }

    /*public static void main(String[] args) {
        SyncAndLock useLock = new SyncAndLock();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                useLock.useLock(Thread.currentThread());
            }
        }, "thread11");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                useLock.useLock(Thread.currentThread());
            }
        }, "thread22");

        thread.start();
        thread1.start();
    }
        thread11获取当前锁
        thread11执行完毕释放锁
        thread22获取当前锁
        thread22执行完毕释放锁
    */

    public void tryLockTest(Thread thread){
        if (lock.tryLock()){
            System.out.println(thread.getName() + "获取当前锁");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(thread.getName() + "发生异常释放锁");
            }finally {
                System.out.println(thread.getName() + "执行完毕释放锁");
                lock.unlock();
            }
        }else{
            System.out.println(Thread.currentThread().getName() + "当前锁被别人占用，我无法获取");
        }
    }
   /* public static void main(String[] args) {
        SyncAndLock syncAndLock = new SyncAndLock();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                syncAndLock.tryLockTest(Thread.currentThread());
            }
        }, "thread11");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                syncAndLock.tryLockTest(Thread.currentThread());
            }
        }, "thread22");

        thread.start();
        thread1.start();
    }
    thread11获取当前锁
    thread22当前锁被别人占用，我无法获取
    thread11执行完毕释放锁
    */
   public void tryLockParamTest(Thread thread) throws InterruptedException {
       //尝试获取锁， 获取不到锁，就等3秒，如果3秒后还是获取不到就返回false
       if (lock.tryLock(3000, TimeUnit.MILLISECONDS)){
           try {
               System.out.println(thread.getName() + "获取当前锁");
               Thread.sleep(4000);
               /**
                * thread11获取当前锁
                * thread11执行完毕释放锁
                * thread22获取当前锁
                * thread22执行完毕释放锁
                * ----------------------
                * thread11获取当前锁
                * 我是线程thread22当前锁被别人占用，等待3S后仍无法获取，就放弃
                * thread11执行完毕释放锁
                */
           }finally {
               System.out.println(thread.getName() + "执行完毕释放锁");
               lock.unlock();
           }
       }else{
           System.out.println("我是线程" + Thread.currentThread().getName()+"当前锁被别人占用，等待3S后仍无法获取，就放弃");
       }
   }

    public static void main(String[] args) {
        SyncAndLock syncAndLock = new SyncAndLock();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    syncAndLock.tryLockParamTest(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread11");
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    syncAndLock.tryLockParamTest(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread22");
        thread.start();
        thread1.start();
    }

}
