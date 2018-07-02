package com.pattern.factory.abstrCar;

/**
 * Create by sunyang on 2018/6/30 13:12
 * For me:One handred lines of code every day,make myself stronger.
 */
public class AbstrCarTest {

    public static void main(String[] args) {
        DefaultFactory factory = new DefaultFactory();
        System.out.println(factory.getCar("Benz"));
    }
}
