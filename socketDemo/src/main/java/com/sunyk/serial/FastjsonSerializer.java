package com.sunyk.serial;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

/**
 * Create by sunyang on 2018/6/18 23:13
 * For me:One handred lines of code every day,make myself stronger.
 */
public class FastjsonSerializer implements ISerializer {
    @Override
    public <T> byte[] serializer(T obj) throws IOException {
//        return JSON.toJSONBytes(obj);
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deSerializer(byte[] data, Class<T> clazz) throws IOException {
        return JSON.parseObject(new String(data), clazz);
    }
}
