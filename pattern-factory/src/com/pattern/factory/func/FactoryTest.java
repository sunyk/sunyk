package com.pattern.factory.func;



/**
 * Create by sunyang on 2018/6/25 0:11
 * For me:One handred lines of code every day,make myself stronger.
 */
public class FactoryTest {

    public static void main(String[] args) {
//        System.out.println(new Factory().getMilk());
        Factory factory = (Factory) new TelunsuFactory();
        System.out.println(factory.getMilk());
    }
}
