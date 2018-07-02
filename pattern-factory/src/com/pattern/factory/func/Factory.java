package com.pattern.factory.func;

import com.pattern.factory.Milk;

/**
 * Create by sunyang on 2018/6/25 0:10
 * For me:One handred lines of code every day,make myself stronger.
 */
//工厂接口，就定义所有工厂的执行标准
public interface Factory {

    Milk getMilk();
}
