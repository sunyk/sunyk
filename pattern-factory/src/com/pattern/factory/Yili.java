package com.pattern.factory;

/**
 * Create by sunyang on 2018/6/25 0:01
 * For me:One handred lines of code every day,make myself stronger.
 */
public class Yili implements Milk{
    @Override
    public String getName() {
        System.out.println("伊利");
        return "伊利";
    }
}
