### rabbitmq合并数据10条发送与接收处理如下

~~~java
JSONObject title = new JSONObject();
        List mailMergeList = new ArrayList();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 1){
                mailMergeList.add("biboss@qq.com");
            }else{
                mailMergeList.add("biboss@tuhu.com");
            }
        }
        String jsonBody = JSONObject.toJSONString(mailMergeList);
        title.put("type","add");
        title.put("desc","新增商户");
        title.put("content",jsonBody);
        amqpTemplate.convertAndSend(topicExchange,topicRoutingKey, title.toJSONString());
~~~

----------------

~~~java
@Component
@PropertySource("classpath:properties")
@RabbitListener(queues = "${secondqueue}", containerFactory="rabbitListenerContainerFactory")
public class SecondConsumer {
    @RabbitHandler
    public void process(String msg){
        System.out.println("Second Queue received msg : " + msg );
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String result = String.valueOf(jsonObject.get("content"));
//        JSONObject object = jsonObject.getJSONObject("content");

        List<Object> clazz = JSON.parseArray(result, Object.class);
        for (Object s :clazz){
            System.out.println(s.toString());
        }
    }
}
~~~

---

~~~java
/**
     * 在消费端转换JSON消息
     * 监听类都要加上containerFactory属性
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
~~~

------------------------------------------------------------------------

~~~java
@Configuration
public class RabbitConfig {
    /**
     * 所有的消息发送都会转换成JSON格式发到交换机
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate myTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
~~~

## 其他说明
  Spring AMQP默认使用的消息转换器是SimpleMessageConverter。  
  Jackson2JsonMessageConverter 用于将消息转换为JSON后发送。
  FourthConsumer里面实现了Java对象与JSON报文的转换。