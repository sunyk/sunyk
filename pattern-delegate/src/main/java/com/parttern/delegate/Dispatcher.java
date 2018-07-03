package com.parttern.delegate;

/**
 * Create by sunyang on 2018/6/30 19:20
 * For me:One handred lines of code every day,make myself stronger.
 */
public class Dispatcher implements IExector{
    IExector exector;

    public Dispatcher(IExector exector) {
        this.exector = exector;
    }

    @Override
    public void doing() {
        this.exector.doing();
    }
}
