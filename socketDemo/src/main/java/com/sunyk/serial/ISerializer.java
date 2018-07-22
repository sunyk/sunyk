package com.sunyk.serial;

import java.io.IOException;

/**
 * Create by sunyang on 2018/6/18 14:30
 * For me:One handred lines of code every day,make myself stronger.
 */
public interface ISerializer {

    <T> byte[] serializer(T obj) throws IOException;
    <T> T deSerializer(byte[] data, Class<T> clazz) throws IOException;

}