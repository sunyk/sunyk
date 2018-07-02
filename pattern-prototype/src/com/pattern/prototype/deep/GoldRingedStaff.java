package com.pattern.prototype.deep;

import java.io.Serializable;

/**
 * Create by sunyang on 2018/7/3 0:20
 * For me:One handred lines of code every day,make myself stronger.
 */
public class GoldRingedStaff implements Serializable {

    private float height = 100;
    private  float diameter = 10;//直径

    /**
     * 金箍棒变大
     */
    public void grow(){
        this.diameter *=2;
        this.height*=2;
    }

    /**
     * 金箍棒缩小
     */
    public void  small(){
        this.diameter /=2;
        this.height /=2;
    }
}
