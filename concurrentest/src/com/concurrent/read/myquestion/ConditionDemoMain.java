package com.concurrent.read.myquestion;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * created on sunyang 2018/7/20 16:10
 * Are you different!"jia you" for me
 */
public class ConditionDemoMain {

    public static void main(String[] args) {
        ConditionDemo r = new ConditionDemo();
//        ConditionDemoSynchronized r = new ConditionDemoSynchronized();
        Mutil_Producer pro = new Mutil_Producer(r);
        Mutil_Consume con = new Mutil_Consume(r);



        Thread t0 =  new Thread(pro);
        Thread t1 = new Thread(pro);

        Thread t2 = new Thread(con);
        Thread t3 = new Thread(con);

        t0.start();
        t1.start();

        t2.start();
        t3.start();
    }

    private static class Mutil_Producer implements Runnable{
        private ConditionDemo r;
        public Mutil_Producer(ConditionDemo r) {
            this.r = r;
        }

//        private ConditionDemoSynchronized r;
//
//        public Mutil_Producer(ConditionDemoSynchronized r) {
//            this.r = r;
//        }

        @Override
        public void run() {
            while (true){
                r.producer("北京烤鸭");
            }
        }
    }

    private static class Mutil_Consume implements Runnable{
        private ConditionDemo r;

        public Mutil_Consume(ConditionDemo r) {
            this.r = r;
        }
//        private ConditionDemoSynchronized r;
//
//        public Mutil_Consume(ConditionDemoSynchronized r) {
//            this.r = r;
//        }

        @Override
        public void run() {
            while (true){
                r.consume();
            }
        }
    }
}
