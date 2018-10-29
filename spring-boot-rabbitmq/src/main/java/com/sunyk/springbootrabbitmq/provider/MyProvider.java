package com.sunyk.springbootrabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Create by sunyang on 2018/10/28 23:55
 * For me:One handred lines of code every day,make myself stronger.
 */
@Component
public class MyProvider {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(){
        amqpTemplate.convertAndSend("","FIRST_QUEUE","-----------A direct message");
        amqpTemplate.convertAndSend("TOPIC_EXCHANGE","sunyk.goodnight","a topic is sunyk for goodnight");

        amqpTemplate.convertAndSend("FANOUT_EXCHANGE","","this is new message--------------");
    }
}
