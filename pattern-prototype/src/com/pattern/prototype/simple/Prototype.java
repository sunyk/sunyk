package com.pattern.prototype.simple;

import java.util.ArrayList;

/**
 * Create by sunyang on 2018/7/2 23:36
 * For me:One handred lines of code every day,make myself stronger.
 */
public class Prototype implements Cloneable{
    //@Override
    //浅克隆，浅拷贝
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }

    //深拷贝
    public ArrayList<String> list = new ArrayList<>();
    protected Object clone() throws CloneNotSupportedException {
        Prototype prototype = null;
        try {
            prototype = (Prototype) super.clone();
            prototype.list = (ArrayList<String>) list.clone();
        }catch (Exception e){

        }

        return prototype;
    }
}
