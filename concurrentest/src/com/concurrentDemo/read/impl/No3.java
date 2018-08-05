package concurrentDemo.read.impl;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * created on sunyang 2018/6/29 17:47
 * Are you different!"jia you" for me
 */
public class No3 {
    /**
     * CountDownLatch中使用到的共享锁模型
     * CountDownLatch的方便之处在于，你可以在一个线程中使用，也可以在多个线程上使用，一切只依据状态值，这样便不会受限于任何的场景。
     * 计数器不可重复，如果想重复用@CyclicBarrier
     * 是什么？
     * CountDownLatch也叫闭锁，在JDK1.5被引入，允许一个或多个线程等待其他线程完成操作后再执行。
     * countDownLatch内部会维护一个初始值为线程数量的计数器，主线程执行await方法，如果计数器大于0，则阻塞等待。
     * 当一个线程完成任务后，计数器值减1。当计数器为0时，表示所有的线程已经完成任务，等待的主线程被唤醒继续执行。
     * 做什么？
     * 当一个主线程需要等待其他线程完成任务之后，才能接着往下执行。
     * 此时countDownLatch对象调用了一个await（）方法等待需要完成的进程执行玩，主线程才能接着往下走。
     * 参考https://www.jianshu.com/p/7c7a5df5bda6?ref=myread
     */

    public static void startCountDownLatch(){
        int threadNum = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);

        for (int i = 0; i < 10; i++) {
            final int currentI = i+1;
            new Thread(
                    ()->{
                        System.out.println("Thread " + currentI + "start");
                        Random random = new Random();
                        try {
                            Thread.sleep(random.nextInt(10000) + 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Thread " +currentI + "finished");
                        countDownLatch.countDown();
                    }
            ).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All thread finish;");

    }

    public static void main(String[] args) {
        startCountDownLatch();
    }
}
