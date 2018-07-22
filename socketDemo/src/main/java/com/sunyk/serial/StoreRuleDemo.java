package com.sunyk.serial;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Create by sunyang on 2018/6/18 21:48
 * For me:One handred lines of code every day,make myself stronger.
 */
public class StoreRuleDemo {

    public static void main(String[] args) throws IOException {

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("user")));

        User user = new User();
        user.setSex("男");
        user.setName("mic");
        user.setAge(18);
        user.setHobby("菲菲");
        objectOutputStream.flush();
        objectOutputStream.writeObject(user);
        System.out.println(new File("user").length());
        objectOutputStream.writeObject(user);
        objectOutputStream.flush();
        System.out.println(new File("user").length());
        objectOutputStream.writeObject(user);
        objectOutputStream.flush();
        objectOutputStream.close();
        System.out.println(new File("user").length());

    }
}
