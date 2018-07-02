package com.pattern.prototype.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by sunyang on 2018/6/26 0:09
 * For me:One handred lines of code every day,make myself stronger.
 */
public class Prototypr implements Cloneable {

    private String name;

    public ArrayList<String> list;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
