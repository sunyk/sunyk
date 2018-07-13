package com.pattern.strategy.pay;

import com.pattern.strategy.pay.payport.PayType;
import com.pattern.strategy.pay.payport.Payment;

/**
 * Create by sunyang on 2018/6/23 23:00
 * For me:One handred lines of code every day,make myself stronger.
 */
public class Order {
    private String uid;
    private String orderId;
    private double amount;

    public Order(String uid, String orderId, double amount) {
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
    }

    public PayState pay(PayType payType){
        return payType.get().pay(this.uid,this.amount);
    }
}
