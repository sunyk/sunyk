package com.pattern.strategy.pay.payport;

import com.pattern.strategy.pay.PayState;

/**
 * Create by sunyang on 2018/6/23 23:13
 * For me:One handred lines of code every day,make myself stronger.
 */
public class WechatPay implements Payment {
    @Override
    public PayState pay(String uid, double amount) {
        System.out.println("欢迎使用微信支付");
        return new PayState(200,"支付成功", amount);
    }
}
