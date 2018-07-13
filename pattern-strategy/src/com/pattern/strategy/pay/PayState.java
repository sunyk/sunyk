package com.pattern.strategy.pay;

/**
 * Create by sunyang on 2018/6/23 23:04
 * For me:One handred lines of code every day,make myself stronger.
 */
public class PayState {

    private int code;
    private Object data;
    private String msg;

    public PayState(int code, String msg, Object data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "PayState{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
