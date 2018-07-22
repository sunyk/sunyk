package com.concurrent.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created on sunyang 2018/7/13 16:11
 * Are you different!"jia you" for me
 */
public class Application {

    private CountDownLatch latch;
    public void startUp() throws InterruptedException {
        latch = new CountDownLatch(2);
        List<Service> services = new ArrayList<>();
        services.add(new DatabaseCheckerService(latch));
        services.add(new HealthCheckService(latch));
        ExecutorService pool = Executors.newFixedThreadPool(services.size());
        for (Service  service: services) {
            pool.execute(service);
        }
        latch.await();
        System.out.println("all service is start up");
    }


}
