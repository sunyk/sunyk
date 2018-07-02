package com.pattern.prototype.deep;

import java.io.Serializable;

/**
 * Create by sunyang on 2018/6/26 0:19
 * For me:One handred lines of code every day,make myself stronger.
 */
public class JinGuBang implements Serializable {

    private float h = 100;
    private float d = 10;

    public void big(){
        this.d *= 2;
        this.h *= 2;
    }

    public void small(){
        this.d /=2;
        this.h /=2;
    }
}
