package concurrentDemo.read.impl;

/**
 * created on sunyang 2018/7/12 18:30
 * Are you different!"jia you" for me
 */
public class No7 {
    /**
     * CountDownLatch与CyclicBarrier区别
     * CountDownLatch也叫闭锁，在JDK1.5被引入，允许一个或多个线程等其他线程完成操作后再执行。
     * CountDownLatch内部会维护一个初始值为线程数量的计数器，主线程执行await方法，如果计数器
     * 大于0，则阻塞等待。当一个线程完成任务后，计数器值减1。当计数器为0时，表示所有的线程
     * 已经完成任务，等待主线程被唤醒继续执行。
     * 实现原理：
     * CountDownLatch实现主要基于java同步器AQS，
     *
     * 和CyclicBarrier的区别
     * 1.CyclicBarrier允许一系列线程相互等待对方到达一个点，正如barrier表示的意思，该点就像一个栅栏，先到达的线程被阻塞在栅栏前，
     * 必须等到所有线程都到达了才能通过栅栏；
     * 2.CyclicBarrier持有一个变量parties，表示需要全部到达的线程数量；
     * 先到达的线程调用barrier.await方法进行等待，一旦到达的线程数达到parties变量所指定的书，栅栏打开，所有线程都可以通过；
     * 3.CyslicBarrier构造方法接受另外一个Runnable类型参数barrierAction，该参数表明在栅栏被打开的时候需要采取的动作，null表示不采取任何动作，
     * 注意该动作将会在栅栏被打开而所有线程接着运行前被执行；
     * 4.CyclicBarrier是重用的，当最后一个线程到达的时候，栅栏被打开，所有线程通过之后栅栏重新关闭，进入下一代；
     * 5.CyclicBarrier.reset方法能够手动重置栅栏，此时正在等待的线程会收到BrokenBarrierException异常
     */


}
