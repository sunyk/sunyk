package com.sunyk.serial;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.IOException;

/**
 * Create by sunyang on 2018/6/18 22:48
 * For me:One handred lines of code every day,make myself stronger.
 */
public class XmlSerializer implements ISerializer {

    XStream xStream = new XStream(new DomDriver());

    @Override
    public <T> byte[] serializer(T obj) throws IOException {
        return xStream.toXML(obj).getBytes();
    }

    @Override
    public <T> T deSerializer(byte[] data, Class<T> clazz) throws IOException {
        return (T)xStream.fromXML(new String(data));
    }
}
