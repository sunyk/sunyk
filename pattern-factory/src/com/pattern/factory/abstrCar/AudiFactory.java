package com.pattern.factory.abstrCar;

import com.pattern.factory.car.Audi;
import com.pattern.factory.car.Car;
import com.pattern.factory.funcCar.Factory;

/**
 * Create by sunyang on 2018/6/30 12:10
 * For me:One handred lines of code every day,make myself stronger.
 */
public class AudiFactory extends AbstractCarFactory{
    @Override
    public Car getCar() {
        return new Audi();
    }
}
