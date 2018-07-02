package com.pattern.factory.funcCar;

import com.pattern.factory.Milk;
import com.pattern.factory.car.Car;

/**
 * Create by sunyang on 2018/6/25 0:10
 * For me:One handred lines of code every day,make myself stronger.
 */
//工厂接口，就定义所有工厂的执行标准
public interface Factory {

    //符合汽车上路标准
    Car getCar();
}
