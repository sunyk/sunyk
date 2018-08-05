package com.concurrent.locks;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * created on sunyang 2018/6/25 15:21
 * Are you different!"jia you" for me
 */
public interface Condition {
    //阻塞
    void await() throws InterruptedException;
    //阻塞不间断
    //当前线程进入等待状态，直到被唤醒，该方法不响应中断要求
    void awaitUnInterrupted();
    //调用该方法，当前线程进入等待状态，直到被唤醒或被中断或超时
    //其中nanosTimeout指的等待超时时间，单位纳秒
    long awaitNanos(long nanosTimeOut) throws InterruptedException;
    //同awaitNanos，但可以指明时间单位
    boolean await(long time, TimeUnit unit) throws InterruptedException;
    //调用该方法当前线程进入等待状态，直到被唤醒、中断或到达某个时
    //间期限(deadline),如果没到指定时间就被唤醒，返回true，其他情况返回false
    boolean awaitUntil(Date dealDate) throws InterruptedException;
    //唤醒一个等待在Condition上的线程，该线程从等待方法返回前必须
    //获取与Condition相关联的锁，功能与notify()相同
    void singal();
    //唤醒所有等待在Condition上的线程，该线程从等待方法返回前必须
    //获取与Condition相关联的锁，功能与notifyAll()相同
    void singalAll();

}
