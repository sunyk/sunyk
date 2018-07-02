package com.pattern.factory.funcCar;

import com.pattern.factory.car.Audi;
import com.pattern.factory.car.Car;

/**
 * Create by sunyang on 2018/6/30 12:10
 * For me:One handred lines of code every day,make myself stronger.
 */
public class AudiFactory implements Factory{
    @Override
    public Car getCar() {
        return new Audi();
    }
}
