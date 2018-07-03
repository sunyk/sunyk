package com.parttern.delegate;

/**
 * Create by sunyang on 2018/6/30 19:24
 * For me:One handred lines of code every day,make myself stronger.
 */
public class DispatcherTest {


    public static void main(String[] args) {
        //
        Dispatcher dispatcher = new Dispatcher(new ExectorA());
        dispatcher.doing();
    }
}
