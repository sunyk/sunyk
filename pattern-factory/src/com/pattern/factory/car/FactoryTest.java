package com.pattern.factory.car;

/**
 * Create by sunyang on 2018/6/30 11:33
 * For me:One handred lines of code every day,make myself stronger.
 */
public class FactoryTest {
    public static void main(String[] args) {
        //这边就是我们的消费者
        Car car = new SimpleFactory().getCar("Audi");
        System.out.println(car.getName());
    }
}
