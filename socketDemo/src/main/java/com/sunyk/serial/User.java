package com.sunyk.serial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Create by sunyang on 2018/6/18 14:52
 * For me:One handred lines of code every day,make myself stronger.
 */
public class User extends SuperClass{


//    private static final long serialVersionUID = -6379071175247054700L;

    public static int num = 5;

    private transient String hobby;

    //绕过transient的实现方法
    private  void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(hobby);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        hobby = (String) objectInputStream.readObject();
    }


    private String name;

    private int age;

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "Hobby='" + hobby + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
