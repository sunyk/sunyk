package com.sunyk.serial;

import java.io.Serializable;

/**
 * Create by sunyang on 2018/6/18 21:30
 * For me:One handred lines of code every day,make myself stronger.
 */
public class SuperClass implements Serializable {


    private static final long serialVersionUID = 1805255094774777104L;

    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "SuperClass{" +
                "sex='" + sex + '\'' +
                '}';
    }
}
