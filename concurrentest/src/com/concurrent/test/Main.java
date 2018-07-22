package com.concurrent.test;

/**
 * created on sunyang 2018/7/13 16:19
 * Are you different!"jia you" for me
 */

/**
 * 应用场景
 *
 * 应用程序的主线程希望在负责启动框架服务的线程已经完成之后再执行。在例子中，模拟了一个应用的启动类，具体实现如下。
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Application application = new Application();
        application.startUp();
    }
}
