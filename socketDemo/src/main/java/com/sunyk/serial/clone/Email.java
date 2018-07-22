package com.sunyk.serial.clone;

import java.io.Serializable;

/**
 * Create by sunyang on 2018/6/18 22:07
 * For me:One handred lines of code every day,make myself stronger.
 */
public class Email implements Serializable {

    private String context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
