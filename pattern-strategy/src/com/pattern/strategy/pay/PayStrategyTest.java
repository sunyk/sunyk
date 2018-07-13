package com.pattern.strategy.pay;

import com.pattern.strategy.pay.Order;
import com.pattern.strategy.pay.payport.AliPay;
import com.pattern.strategy.pay.payport.PayType;

/**
 * Create by sunyang on 2018/6/23 23:10
 * For me:One handred lines of code every day,make myself stronger.
 */
public class PayStrategyTest {

    public static void main(String[] args) {
        Order order = new Order("1","200001",100);

        //开始支付
//        order.pay();
//        System.out.println(order.pay(new AliPay()));
        System.out.println(order.pay(PayType.ALI_PAY));
    }

}
