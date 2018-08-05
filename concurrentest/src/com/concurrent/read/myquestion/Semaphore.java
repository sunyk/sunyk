package com.concurrent.read.myquestion;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * created on sunyang 2018/7/20 17:45
 * Are you different!"jia you" for me
 */
public class Semaphore {
    /*
    信号量-Semaphore
    Semaphore共享锁的使用
    Semaphore实现互斥锁
    Semaphore中共享锁的实现
    Semaphore的实现内部原理概要
    非公平锁中的共享锁
    公平锁中的共享锁
    小结
     */
    /**
     * 信号量-Semaphore
     * Semaphore共享锁的使用
     * 信号量(Semaphore)，又被称为信号灯，在多线程环境下用于协调各个线程, 以保证它们能够正确、合理的使用公共资源。
     * 信号量维护了一个许可集，我们在初始化Semaphore时需要为这个许可集传入一个数量值，该数量值代表同一时间能访问共享资源的线程数量。
     * 线程可以通过acquire()方法获取到一个许可，然后对共享资源进行操作，注意如果许可集已分配完了，那么线程将进入等待状态，直到其他线程释放许可才有机会再获取许可，
     * 线程释放一个许可通过release()方法完成。
     * Semaphore实现互斥锁
     * 在初始化信号量时传入1，使得它在使用时最多只有一个可用的许可，从而可用作一个相互排斥的锁。这通常也称为二进制信号量，因为它只能有两种状态：一个可用的许可或零个可用的许可。
     * 按此方式使用时，二进制信号量具有某种属性（与很多 Lock 实现不同），即可以由线程释放“锁”，而不是由所有者
     * Semaphore中共享锁的实现
     * Semaphore的实现内部原理概要
     * Semaphore内部同样存在继承自AQS的内部类Sync以及继承自Sync的公平锁(FairSync)和非公平锁(NofairSync),从这点也足以说明Semaphore的内部实现原理也是基于AQS并发组件的，
     * 在上一篇文章中，我们提到过，AQS是基础组件，只负责核心并发操作，如加入或维护同步队列，控制同步状态，等，而具体的加锁和解锁操作交由子类完成，
     * 因此子类Semaphore共享锁的获取与释放需要自己实现，这两个方法分别是获取锁的tryAcquireShared(int arg)方法和释放锁的tryReleaseShared(int arg)方法，
     * 这点从Semaphore的内部结构完全可以看出来
     * Semaphore的内部类公平锁(FairSync)和非公平锁(NoFairSync)各自实现不同的获取锁方法即tryAcquireShared(int arg)，毕竟公平锁和非公平锁的获取稍后不同，
     * 而释放锁tryReleaseShared(int arg)的操作交由Sync实现，因为释放操作都是相同的，因此放在父类Sync中实现当然是最好的。需要明白的是，我们在调用Semaphore的方法时，
     * 其内部则是通过间接调用其内部类或AQS执行的。下面我们就从Semaphore的源码入手分析共享锁实现原理，这里先从非公平锁入手。
     * 非公平锁中的共享锁
     * //默认创建公平锁，permits指定同一时间访问共享资源的线程数
     * public Semaphore(int permits) {
     *         sync = new NonfairSync(permits);
     *     }
     *
     * public Semaphore(int permits, boolean fair) {
     *      sync = fair ? new FairSync(permits) : new NonfairSync(permits);
     *      sync =fair ? new FairSync() ： new NonfairSync()
     *  }
     *
     *
     *
     */

    public static void main(String[] args) {
        final java.util.concurrent.Semaphore mutex = new java.util.concurrent.Semaphore(1);
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index  = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        mutex.acquire();
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println(String.format("[Thread-%s]任务id --- %s",Thread.currentThread().getId(), index));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        System.out.println("release----");
                        mutex.release();
                    }
                }
            };
            pool.execute(runnable);
        }
        pool.shutdown();
    }


    public static void maintest(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        final java.util.concurrent.Semaphore semaphore = new java.util.concurrent.Semaphore(5);
        for (int  i = 0; i < 20; i++) {
            final int No = i;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        //semaphore使用acquire（）获取锁
                        semaphore.acquire();
                        System.out.println("visitor " + No);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }
                }
            };
            pool.execute(run);
        }
        pool.shutdown();
    }
}
