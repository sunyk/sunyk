package concurrentDemo.read.impl;

/**
 * created on sunyang 2018/6/29 16:46
 * Are you different!"jia you" for me
 */
public class No2 {

    /**
     * sleep() 不会释放锁标志，如果有synchronized同步块，其他线程仍然不能访问共享数据
     * wait() 释放锁标志，调用wait（） notify（）notifyAll（）必须在synchronized语句块内使用（因为这三个任务在调用这些方法前必须拥有对象的锁）
     * ReenTrantLock实现同步，await() signal() signalAll() 必须在ReenTrantLock.newCondition()获得Condition类对象
     * yield() 不会释放锁标志，没有参数，yield()方法只能使同优先级或高优先级的线程得到执行机会
     * join() 会使当前线程等待调用join（）方法的线程结束后才能继续执行
     */

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new my());
        thread.start();

        for (int i = 0; i < 20; i++) {
            System.out.println("主线程第"+i +"次执行！");
            if (i >= 5){
                thread.join();
            }
        }
    }

    static class my implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("子线程执行"+i+"次");
            }
        }
    }
}
