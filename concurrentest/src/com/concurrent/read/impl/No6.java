package com.concurrent.read.impl;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * created on sunyang 2018/7/12 16:25
 * Are you different!"jia you" for me
 */
public class No6 {
    /**
     * 1.是什么
     * Exchanger是jdk1.5以后提供的工具类，是用于两个工作线程之间交换数据。
     * 当一个线程到达exchange调用点时，如果他的伙伴线程此前已经调用了此方法，那么他的伙伴会被调度唤醒并与之进行对象交换，然后各自返回。
     * 如果他的伙伴线程还没到达交换点，那么当前线程会挂起，等到伙伴线程到达-完成交换正常返回；或者当前线程被中断-抛出异常；或者等待超时-抛出超时异常
     *
     */

    private static volatile boolean isDone = false;

    //生产模式
    static class ExchangeProducer implements Runnable{
        private Exchanger<Integer> exchanger;
        private static int data = 1;

        public ExchangeProducer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            while (!Thread.interrupted() && !isDone){
                for (int i = 1;i< 4;i++){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        data = i;
//                        System.out.println("producer before:" + data);
                        data = exchanger.exchange(data);
//                        System.out.println("producer after:" + data);
                        System.out.println("producer result:" + data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isDone = true;
            }
        }
    }
    //消费模式
    static class ExchangeConsumer implements Runnable {
        private Exchanger<Integer> exchanger;
        private static int data = 0;

        public ExchangeConsumer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            while (!Thread.interrupted() && !isDone){
                data = 100;
//                System.out.println("consumer before:" + data);
                try {
                    TimeUnit.SECONDS.sleep(1);
                    data = exchanger.exchange(data);
//                    System.out.println("consumer after:" + data);
                    System.out.println("consumer result:" + data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        Exchanger<Integer> exchanger = new Exchanger<>();
        ExchangeProducer producer = new ExchangeProducer(exchanger);
        ExchangeConsumer consumer = new ExchangeConsumer(exchanger);
        pool.execute(producer);
        pool.execute(consumer);
        pool.shutdown();
        try {
            pool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
