package com.sunyk.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by sunyang on 2018/10/28 23:44
 * For me:One handred lines of code every day,make myself stronger.
 */
@Configuration
public class RabbitConfig {

    //this is a direct sender queue...
    @Bean("firstQueue")
    public Queue getFirstQueue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 6000);
        Queue queue = new Queue("FIRST_QUEUE",false,false,true, args);
        return queue;
    }

    //The Second queue for topicExchange...
    //1.create topicExchange
    //2.binding queue and topicExchange
    @Bean("topicExchange")
    public TopicExchange getTopicExchange(){
        return new TopicExchange("TOPIC_EXCHANGE");
    }

    @Bean("secondQueue")
    public Queue getSecondQueue(){
        return new Queue("SECOND_QUEUE");
    }

    @Bean
    public Binding bindingSecondQueue(@Qualifier("secondQueue") Queue queue,
                                      @Qualifier("topicExchange") TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("sunyk#");
    }

    //this third...
    @Bean("fanoutExchange")
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("FANOUT_EXCHANGE");
    }

    @Bean("thirdQueue")
    public Queue getThirdQueue(){
        return new Queue("THIRD_QUEUE");
    }

    @Bean
    public Binding bindingThirdQueue(@Qualifier("thirdQueue") Queue queue,
                                      @Qualifier("fanoutExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }



}
