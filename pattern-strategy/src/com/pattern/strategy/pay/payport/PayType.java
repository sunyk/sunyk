package com.pattern.strategy.pay.payport;

/**
 * Create by sunyang on 2018/6/23 23:22
 * For me:One handred lines of code every day,make myself stronger.
 */
public enum PayType {
    ALI_PAY(new AliPay()),
    WECHAT_PAY(new WechatPay());

    private Payment payment;

    PayType(Payment payment) {
        this.payment = payment;
    }

    public Payment get(){
        return this.payment;
    }

}
