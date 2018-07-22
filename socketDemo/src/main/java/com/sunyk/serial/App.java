package com.sunyk.serial;

import java.io.IOException;

/**
 * Create by sunyang on 2018/6/18 14:52
 * For me:One handred lines of code every day,make myself stronger.
 */
public class App {

    public static void main(String[] args) throws IOException {
//        ISerializer iSerializer = new JavaSerializer();
        ISerializer iSerializer = new FileSerializer();
        User user = new User();
        user.setName("mic");
        user.setHobby("aaa");
        user.setAge(18);
        user.setSex("男");
        byte[] bytes = iSerializer.serializer(user);//Java序列化
//        User.num = 10;

        User user1 = iSerializer.deSerializer(bytes, User.class);
        System.out.println(user1+":" + user1.getSex());
    }
}
