package txz.study.example.springamqp.pulishconfirm;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Administrator on 2018/6/27.
 */
@Profile({"ack"})
@Configuration
public class ConfirmConfig {

    @Bean
    public Queue QueueA() {
        return new Queue("hello");
    }

    @Bean
    public Queue QueueB() {
        return new Queue("helloObj");
    }

    /**
     * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("ABExchange");
    }


    @Bean
    Binding bindingExchangeA(Queue QueueA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(QueueA).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue QueueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(QueueB).to(fanoutExchange);
    }

    @Bean
    ConfirmReceiver confirmReceiver1() {
        return new ConfirmReceiver();
    }

     @Bean
    ConfirmReceiver2 confirmReceiver2() {
        return new ConfirmReceiver2();
    }

/*开启消息确认

    @Bean
    public ConnectionFactory createConnectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1", 5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        connectionFactory.setPublisherReturns(true);
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }*/
}
