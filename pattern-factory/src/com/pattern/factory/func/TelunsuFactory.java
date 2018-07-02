package com.pattern.factory.func;

import com.pattern.factory.Milk;
import com.pattern.factory.Telunsu;
import com.pattern.factory.func.Factory;

/**
 * Create by sunyang on 2018/6/25 0:15
 * For me:One handred lines of code every day,make myself stronger.
 */
public class TelunsuFactory implements Factory {
    @Override
    public Milk getMilk() {
        return new Telunsu();
    }
}
