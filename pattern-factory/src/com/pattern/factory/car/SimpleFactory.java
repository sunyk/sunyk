package com.pattern.factory.car;

/**
 * Create by sunyang on 2018/6/30 11:31
 * For me:One handred lines of code every day,make myself stronger.
 */
public class SimpleFactory {

    //实现统一管理，专业化管理
    //如果没有工程模式，小作坊，没有执行标准的
    //如果买到三无产品（没有标准）
    public Car getCar(String car){
        System.out.println(car);
        if ("BMW".equalsIgnoreCase(car)){
            return  new BMW();
        }
        return new Audi();
    }
}
