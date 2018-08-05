package com.concurrent.read.myquestion;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * created on sunyang 2018/7/20 16:10
 * Are you different!"jia you" for me
 */
public class ConditionDemoSynchronized {
    private String name;
    private int count = 1;
    private boolean flag = false;


    public synchronized void producer(String name){
        while (flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.name = name + count;
        count++;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "生产者..." + this.name);
        flag = true;
        notifyAll();//通知消费者
    }

    public synchronized void consume(){
        while (!flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"消费者..." + this.name);
        flag = false;
        notifyAll();
    }
}
