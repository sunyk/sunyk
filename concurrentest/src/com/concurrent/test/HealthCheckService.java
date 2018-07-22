package com.concurrent.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * created on sunyang 2018/7/13 16:09
 * Are you different!"jia you" for me
 */
public class HealthCheckService extends Service{
    public HealthCheckService(CountDownLatch latch) {
        super(latch);
    }

    //重写父类方法
    @Override
    public void execute() {
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("execute HealthCheckService...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
