package com.concurrent.read.myquestion;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * created on sunyang 2018/7/20 10:31
 * Are you different!"jia you" for me
 */
public class AtomicDemo {
    static AtomicIntegerArray arr = new AtomicIntegerArray(10);

    public static class AddThread implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                arr.getAndIncrement(i%arr.length());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        //创建10条线程
        for (int i = 0; i < 10; i++) {
            ts[i] = new Thread(new AddThread());
        }
        //启动10条线程
        for (int i = 0; i < 10; i++) {
            ts[i].start();
        }
        for (int i =0;i<10;i++){
            ts[i].join();
        }
        //[10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000]
        System.out.println(arr);
    }
}
