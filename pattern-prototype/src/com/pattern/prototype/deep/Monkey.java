package com.pattern.prototype.deep;

import java.util.Date;

/**
 * Create by sunyang on 2018/6/26 0:17
 * For me:One handred lines of code every day,make myself stronger.
 */
public class Monkey {

    public int height;
    public int width;
    public Date birthday;

    public Monkey() {
        this.birthday = birthday;
    }

    public Monkey(int height, int width,Date birthday) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
