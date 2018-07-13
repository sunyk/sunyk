package com.pattern.strategy.pay.payport;

import com.pattern.strategy.pay.PayState;

/**
 * Create by sunyang on 2018/6/23 23:07
 * For me:One handred lines of code every day,make myself stronger.
 */
public interface Payment {

    PayState pay(String uid, double amount);
}
