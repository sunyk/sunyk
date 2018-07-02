package com.pattern.factory.abstr;

import com.pattern.factory.Milk;
import com.pattern.factory.func.MengniuFactory;
import com.pattern.factory.func.TelunsuFactory;
import com.pattern.factory.func.YiliFactory;

/**
 * Create by sunyang on 2018/6/25 0:24
 * For me:One handred lines of code every day,make myself stronger.
 */
public class MilkFactory extends AbstractFactory{
    @Override
    Milk getMengniu() {
        return new MengniuFactory().getMilk();
    }

    @Override
    Milk getYili() {
        return new YiliFactory().getMilk();
    }

    @Override
    Milk getTelunsu() {
        return new TelunsuFactory().getMilk();
    }
}
