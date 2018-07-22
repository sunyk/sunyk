package concurrent.read.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * created on sunyang 2018/6/29 15:26
 * Are you different!"jia you" for me
 */
public class No1{
    /**
     * 继承一个Thread方法，重写run方法
     * 实现一个Runnable接口，重写run方法
     * 使用Future和Callable接口（Lambda）
     */

    //--------------------------------------三种创建线程方法对比-----------------------------------
    /**
     * 实现Runnable和实现Callable接口的方式基本相同，不过是后者执行call()方法有返回值，后者线程执行体run()方法无返回值，
     * 因此可以把这两种方式归为一种这种方式与继承Thread类的方法之间的差别如下：
     *
     * 1、线程只是实现Runnable或实现Callable接口，还可以继承其他类。
     *
     * 2、这种方式下，多个线程可以共享一个target对象，非常适合多线程处理同一份资源的情形。
     *
     * 3、但是编程稍微复杂，如果需要访问当前线程，必须调用Thread.currentThread()方法。
     *
     * 4、继承Thread类的线程类不能再继承其他父类（Java单继承决定）。
     *
     * 注：一般推荐采用实现接口的方式来创建多线程
     */


    /**
     * 通过继承Thread类来创建并启动多线程的一般步骤如下
     *
     * 1】d定义Thread类的子类，并重写该类的run()方法，该方法的方法体就是线程需要完成的任务，run()方法也称为线程执行体。
     *
     * 2】创建Thread子类的实例，也就是创建了线程对象
     *
     * 3】启动线程，即调用线程的start()方法
     */
    static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("继承一个Thread方法，的重写");
        }
    }

    /**
     * 通过实现Runnable接口创建并启动线程一般步骤如下：
     * 定义Runnable接口的实现类，重写run（）方法，
     * 创建Runnable实现类的实例，并用这个实例作为Thread的target来创建Thread对象，这个Thread对象才是真正的线程对象
     * 依然通过调用线程的start（）来启动线程
     */
    static class MyRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println("实现一个Runnable接口，的重写");
        }
    }


    /**
     * -----------------------使用Callable和Future创建线程---------------------
     * 创建Callable接口的实现类，并实现call（）方法，然后创建该实现类的实例
     * 使用FutureTask类来包装Callable对象，该FutureTask对象封装了Callable对象的call()方法的返回值
     * 使用FutureTask对象作为Thread对象的target创建并启动该线程（因为FutureTask实现了Runnable接口）
     * 调用FutureTask对象的get()方法来获得子线程执行结束的返回值
     */

    public static void main(String[] args) {
        //MyThread
        new MyThread().start();
        //MyRunnable
        new Thread(new MyRunnable()).start();
        //Callable和Future
        FutureTask<Integer> futureTask  = new FutureTask<Integer>(
                (Callable<Integer>)()->{
                    System.out.println("Lambda表达式创建Callable对象");
                    return 5;
                }
        );
        FutureTask<Object> task = new FutureTask<Object>(
                (Callable<Object>)()->{
                    Integer object = new Integer("125");
                    return object;
                }
        );

        new Thread(task,"有返回值的线程").start();//实质上还是以Callable对象来创建并启动线程
        try {
            System.out.println("子线程的返回值："+ task.get());//get()方法会阻塞，知道子线程执行结束才返回
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
