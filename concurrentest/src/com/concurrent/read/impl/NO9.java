package com.concurrent.read.impl;

import java.util.concurrent.*;

/**
 * created on sunyang 2018/7/10 17:57
 * Are you different!"jia you" for me
 */
public class NO9 {
    /**
     * 1.线程池是什么
     * java.util.concurrent.Executors提供了一个java.util.concurrent.Executor接口的实现用于创建线程池多线程技术主要解决处理器单元内多个线程执行的问题，他可以显著减少处理器
     * 单元的闲置时间，增加处理器单元的吞吐能力.
     * 一个线程池包括四个基本组成部分：
     * 1、线程池管理器ThreadPool
     * 2、工作线程PoolWorker
     * 3、任务接口Task
     * 4、任务队列taskQueue
     *
     * 常见的线程池
     * 1、newSingleThreadExecutor 单个线程的线程池，即线程池中每次只有一个线程工作，单线程串行执行任务
     * 2、newFixedThreadExecutor(n) 固定数量的线程池，没提交一个任务就是一个线程，直到达到线程池的最大数量，然后后面进入等待队列直到前面的任务完成才能继续执行
     * 3、newCacheThreadExecutor 可缓存线程池，当线程池大小超过了处理任务所需的线程，那么就会回收部分空闲（一般是60秒无执行）的线程，当有任务来时，又智能的添加新线程来执行。
     * 4、newScheduleThreadExecutor 大小无限制的线程池，支持定时和周期性的执行线程
     *
     *
     * 2.线程池的作用：
     * 线程池的作用就是限制系统中执行线程的数量。
     * 根据系统的环境情况，可以自动或手动设置线程数量，达到运行的最佳效果；少了浪费系统资源，多了造成系统拥挤效率不高。用线程池控制线程的数量，其他线程排队等待。一个任务
     * 执行完毕，再从队列的中取最前面的任务开始执行。若队列中没有等待进程，线程池的这一资源处于等待。当一个新任务需要运行时，如果线程中有等待的工作线程，就可以开始运行了；
     * 否则进入等待队列。
     *
     * 3.为什么要用线程池
     * 1、减少了创建和销毁线程的次数，每个工作线程都可以被重复利用，可执行多个任务。
     * 2、可以根据系统的承受能力，调整线程池中工作线程的数目，防止因为消耗过多的内存，而把服务器累趴下（每个线程大约128k内存）
     */

    public static class  MyThread extends Thread{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "正在执行......");
        }
    }

    /*public static void main(String[] args) throws InterruptedException {

        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();
        Thread t5 = new MyThread();
        pool.execute(t1);
        Thread.sleep(1000);
        pool.execute(t2);
        Thread.sleep(1000);
        pool.execute(t3);
        Thread.sleep(1000);
        pool.execute(t4);
        Thread.sleep(1000);
        pool.execute(t5);
        pool.shutdown();
    }*/

    public static void main1(String[] args) throws InterruptedException {
        //创建一个可重用的固定线程数的线程池,单例线程
//        ExecutorService pool = Executors.newSingleThreadExecutor();
        //创建一个可重用固定线程数的线程池
//        ExecutorService pool = Executors.newFixedThreadPool(2);
        //创建一个缓存线程的线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();
        Thread t5 = new MyThread();
        //将线程放到池中执行
        pool.execute(t1);
//        Thread.sleep(100);
        pool.execute(t2);
//        Thread.sleep(100);
        pool.execute(t3);
//        Thread.sleep(100);
        pool.execute(t4);
//        Thread.sleep(100);
        pool.execute(t5);
        //关闭线程池
        pool.shutdown();
    }

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("需要执行==========" + Thread.currentThread().getName());
            }
        }, 5000, 20000, TimeUnit.MILLISECONDS);
        //initialDelay 第一次延迟加载时间
        //period 之后的周期为多少时间执行一次

        /*exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+ "-" + System.nanoTime());
            }
        },1000, 2000, TimeUnit.MILLISECONDS);*/
    }

    /**
     * ThreadPoolExecutor详解
     * corePoolSize - 池中所保存的线程数，包括空闲线程
     * maximumPoolSize - 池中允许的最大线程数
     * keepAliveTime - 当线程数大于核心时候，此为终止前多余的空闲线程等待新任务的最长时间
     * unit - keepAliveTime 参数的时间单位
     * workQueue - 执行前用于保持任务的队列。此队列仅保持由execute方法提交的Runnable任务
     * threadFactory - 执行程序创建新线程时使用的工厂
     * handler - 由于超出线程范围和队列容量而使执行被阻塞时候所使用的处理程序。
     * ThreadPookExecutor是Executors类的底层实心。
     *
     * 在JDK帮助文档中，有如此一段话：
     *
     * “强烈建议程序员使用较为方便的Executors工厂方法Executors.newCachedThreadPool()（无界线程池，可以进行自动线程回收）、
     * Executors.newFixedThreadPool(int)（固定大小线程池）Executors.newSingleThreadExecutor()（单个后台线程）
     *
     * new ThreadPoolExecutor(nthread, nthread , 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
     */


}
