package com.pattern.factory.abstrCar;

import com.pattern.factory.car.Car;

/**
 * Create by sunyang on 2018/6/30 13:09
 * For me:One handred lines of code every day,make myself stronger.
 */
public class DefaultFactory extends  AbstractCarFactory{

    private AudiFactory defaultFactory = new AudiFactory();

    @Override
    protected Car getCar() {
        return defaultFactory.getCar();
    }
}
