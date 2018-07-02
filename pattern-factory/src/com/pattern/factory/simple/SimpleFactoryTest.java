package com.pattern.factory.simple;

/**
 * Create by sunyang on 2018/6/24 23:59
 * For me:One handred lines of code every day,make myself stronger.
 */
public class SimpleFactoryTest {


    public static void main(String[] args) {

        SimpleFactory simpleFactory = new SimpleFactory();
        System.out.println(simpleFactory.getMilk("特仑苏"));
    }
}
