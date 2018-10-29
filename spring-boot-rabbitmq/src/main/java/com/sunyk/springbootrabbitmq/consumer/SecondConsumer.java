package com.sunyk.springbootrabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Create by sunyang on 2018/10/29 0:22
 * For me:One handred lines of code every day,make myself stronger.
 */
@Component
@RabbitListener(queues = "SECOND_QUEUE")
public class SecondConsumer {

    @RabbitHandler
    public void process(String msg){
        System.out.println("this is topic msg ： " + msg );
    }
}
