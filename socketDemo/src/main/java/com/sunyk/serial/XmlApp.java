package com.sunyk.serial;

import com.sun.org.apache.xpath.internal.operations.String;

import java.io.IOException;

/**
 * Create by sunyang on 2018/6/18 14:52
 * For me:One handred lines of code every day,make myself stronger.
 */
public class XmlApp {

    public static void main(java.lang.String[] args) throws IOException {
//        ISerializer iSerializer = new XmlSerializer();
        ISerializer iSerializer = new FastjsonSerializer();
        User user = new User();
        user.setName("mic");
        user.setHobby("aaa");
        user.setAge(18);
        user.setSex("男");
        byte[] rs = iSerializer.serializer(user);//Java序列化

        System.out.println(new java.lang.String(rs));
        User user1 = iSerializer.deSerializer(rs, User.class);
        System.out.println(user1+":" + user1.getSex());
    }
}
