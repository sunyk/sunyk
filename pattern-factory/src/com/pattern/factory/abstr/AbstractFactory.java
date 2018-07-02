package com.pattern.factory.abstr;

import com.pattern.factory.Milk;

/**
 * Create by sunyang on 2018/6/25 0:20
 * For me:One handred lines of code every day,make myself stronger.
 */
public abstract class AbstractFactory {

    /**
     * 获得一个蒙牛品牌的牛奶
     * @return
     */
    abstract Milk getMengniu();

    abstract Milk getYili();

    abstract Milk getTelunsu();
}
