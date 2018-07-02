package com.pattern.factory.simple;

import com.pattern.factory.Mengniu;
import com.pattern.factory.Milk;
import com.pattern.factory.Telunsu;
import com.pattern.factory.Yili;

/**
 * Create by sunyang on 2018/6/24 23:58
 * For me:One handred lines of code every day,make myself stronger.
 */
public class SimpleFactory {

    public Milk getMilk(String name){
        if ("特仑苏".equals(name)){
            System.out.println("生成特仑苏");
            return new Telunsu();
        }
        if ("伊利".equals(name)){
            return new Yili();
        }
        if ("蒙牛".equals(name)){
            return new Mengniu();
        }
        return null;
    }
}
