package com.pattern.prototype.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by sunyang on 2018/7/2 23:37
 * For me:One handred lines of code every day,make myself stronger.
 */
public class ConcretePrototype extends Prototype{
    private int age;
//    public List list = new ArrayList<String>();
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
