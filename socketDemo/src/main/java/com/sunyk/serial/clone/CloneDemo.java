package com.sunyk.serial.clone;

import java.io.IOException;

/**
 * Create by sunyang on 2018/6/18 22:09
 * For me:One handred lines of code every day,make myself stronger.
 */
public class CloneDemo {

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Email email = new Email();
        email.setContext("今天晚上20:00有课程");
        Person p1 = new Person("Mic");
        p1.setEmail(email);

//        Person p2 = p1.clone();  浅克隆
        Person p2 = p1.deepClone();
        p2.setName("james");
        p2.getEmail().setContext("今天晚上20:30有课程");

        System.out.println(p1.getName()+"->"+p1.getEmail().getContext());
        System.out.println(p2.getName()+"->"+p2.getEmail().getContext());


    }

}
