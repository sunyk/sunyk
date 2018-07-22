package com.sunyk.serial.clone;

import java.io.*;

/**
 * Create by sunyang on 2018/6/18 22:07
 * For me:One handred lines of code every day,make myself stronger.
 */
public class Person implements  Cloneable,Serializable {

    private String name;

    private Email email;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Person clone() throws CloneNotSupportedException {
        return (Person)super.clone();
    }

    public Person deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Person person = (Person) objectInputStream.readObject();
        byteArrayInputStream.close();
        byteArrayOutputStream.close();
        objectInputStream.close();
        objectOutputStream.close();
        return person;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email=" + email +
                '}';
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}
