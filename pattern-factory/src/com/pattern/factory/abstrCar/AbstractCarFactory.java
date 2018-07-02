package com.pattern.factory.abstrCar;

import com.pattern.factory.car.Audi;
import com.pattern.factory.car.BMW;
import com.pattern.factory.car.Car;

/**
 * Create by sunyang on 2018/6/30 12:18
 * For me:One handred lines of code every day,make myself stronger.
 */
public abstract class AbstractCarFactory {
    protected abstract Car getCar();

    public Car getCar(String car){
        System.out.println(car);
        if ("BMW".equalsIgnoreCase(car)){
            return  new BmwFactory().getCar();
        }else if ("Benz".equalsIgnoreCase(car)){
            return new BenzFactory().getCar();
        }
        return new AudiFactory().getCar();
    }
}
