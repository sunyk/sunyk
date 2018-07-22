package com.sunyk.serial;

import java.io.*;

/**
 * Create by sunyang on 2018/6/18 14:36
 * For me:One handred lines of code every day,make myself stronger.
 */
public class JavaSerializer implements ISerializer {

    @Override
    public <T> byte[] serializer(T obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();


        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (objectOutputStream!=null){
                objectOutputStream.close();
            }
            byteArrayOutputStream.close();
        }
        return null;
    }

    @Override
    public <T> T deSerializer(byte[] data, Class<T> clazz) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) objectInputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            if (objectInputStream!=null){
                objectInputStream.close();
            }

            byteArrayInputStream.close();
        }

        return null;
    }
}
