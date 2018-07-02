package com.pattern.prototype.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by sunyang on 2018/7/2 23:39
 * For me:One handred lines of code every day,make myself stronger.
 */
public class CloneTest {

    public static void main(String[] args) {

        ConcretePrototype cp= new ConcretePrototype();
        cp.setAge(18);

//        List<String> list = new ArrayList<>();
//        list.add("sunyk");

        try {
            ConcretePrototype copy = (ConcretePrototype) cp.clone();
            System.out.println(copy.list == cp.list);
            System.out.println(copy.getAge());
            System.out.println(copy == cp);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }
}
