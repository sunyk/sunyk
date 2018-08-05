package com.concurrent.read.impl;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * created on sunyang 2018/7/3 16:13
 * Are you different!"jia you" for me
 */
public class No4 {


    /**
     * CyclicBarrier原理
     * CyclicBarrier是一个同步类，在一组线程中，线程间互相等待，达到某个公共屏障点（同步点），
     * 即相互等待的线程都完成调用await方法，所有被屏障的线程才会继续运行await后面的程序。
     * 在涉及一组固定大小的线程的程序中，这些线程必须不时地相互等待，此时CyclicBarrier很有用。
     * 因为屏障点在释放等待线程后可以重用，所以称为循环的屏障点。
     * CyclicBarrier支持一个可选的Runnable命令，在一组线程中的最后一个线程到达屏障点之后
     * （但在释放所有线程之前），该命令只在所有线程到达屏障点之后运行一次，并且该命令由
     * 最后一个进入屏障点的线程执行。
     *
     * private void breakBarrier(){
     *     generation.broken = true;
     *     count  = parties;
     *     trip.signalAll();
     * }
     *
     * public CyclicBarrier(int parties,Runnable barrierAction){
     *     this.parties = parties;
     *     this.count = parties;
     *     this.barrierCommand = barrierAction;
     *
     *     parties表示barrier开启需要到达的线程数量；
     *     count表示等待到达barrier的线程数量；
     *     barrierCommand表示开启barrier后执行的操作；
     * }
     */

    private static void cyclicBarrierA(){
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(10,()->{
            System.out.println("all thread num 10 到达" );
        });

        for (int i = 0; i < 10; i++) {
            final int finalI = i+1;
            new Thread(()->{
                System.out.println("Thread " + finalI + " is started");
                Random random = new Random();
                try {
                    Thread.sleep(random.nextInt(10000) + 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread" + finalI +"到达");
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("thread " + finalI + "to contine");

            }).start();

        }

    }

    public static void main(String[] args) {
        cyclicBarrierA();
    }
}
