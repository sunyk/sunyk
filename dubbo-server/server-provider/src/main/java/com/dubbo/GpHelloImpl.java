package com.dubbo;

/**
 * Create by sunyang on 2018/6/24 15:47
 * For me:One handred lines of code every day,make myself stronger.
 */
public class GpHelloImpl implements IGpHello{
    @Override
    public String sayHello(String msg) {
        System.out.println("Server receive" + msg);
        return "Hello" + msg;
    }
}
