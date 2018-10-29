package com.sunyk.springbootrabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Create by sunyang on 2018/10/28 23:58
 * For me:One handred lines of code every day,make myself stronger.
 */
@Component
@RabbitListener(queues = "FIRST_QUEUE")
public class FirstConsumer {

    @RabbitHandler
    public void process(String msg){
        System.out.println("this is a first queue consumer:"+ msg);
    }
}
