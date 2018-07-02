package com.pattern.factory.funcCar;

/**
 * Create by sunyang on 2018/6/30 12:12
 * For me:One handred lines of code every day,make myself stronger.
 */
public class funcTest {


    public static void main(String[] args) {
        //工厂方法模式
        //各自产品的生产商，都拥有各自的工厂
        Factory factory = new AudiFactory();
        System.out.println(factory.getCar());

        factory = new BenzFactory();
        System.out.println(factory.getCar());
    }
}
