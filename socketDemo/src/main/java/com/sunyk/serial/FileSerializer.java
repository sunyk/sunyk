package com.sunyk.serial;

import java.io.*;

/**
 * Create by sunyang on 2018/6/18 14:36
 * For me:One handred lines of code every day,make myself stronger.
 */
public class FileSerializer implements ISerializer {

    @Override
    public <T> byte[] serializer(T obj) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("user")));
            objectOutputStream.writeObject(obj);

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (objectOutputStream!=null){
                objectOutputStream.close();
            }
        }
        return null;
    }

    @Override
    public <T> T deSerializer(byte[] data, Class<T> clazz) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(new File("user")));
            return (T) objectInputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            if (objectInputStream!=null){
                objectInputStream.close();
            }
        }

        return null;
    }
}
