package com.concurrent.read.myquestion;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * created on sunyang 2018/7/20 16:10
 * Are you different!"jia you" for me
 */
public class ConditionDemo {
    private String name;
    private int count = 1;
    private boolean flag = false;

    Lock lock = new ReentrantLock();

    Condition producer_con = lock.newCondition();
    Condition consumer_con = lock.newCondition();

    public synchronized void getIncrease(){
        count++;
    }

    public void producer(String name){
        lock.lock();
        try {
            while (flag){
                try {
                    System.out.println("生产者者在等待中---");
                    producer_con.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.name = name + count;
            getIncrease();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "生产者..." + this.name);
            flag = true;
            consumer_con.signalAll();//直接唤醒消费者
        }finally {
            lock.unlock();
        }
    }

    public void consume(){
        lock.lock();
        try {
            while(!flag){
                try {
                    System.out.println("消费者在等待中---");
                    consumer_con.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "消费中..." + this.name);
            flag = false;
            producer_con.signal();//直接通知生产者
        }finally {
            lock.unlock();
        }
    }

}
