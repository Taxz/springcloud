package txz.study.example.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by Administrator on 2018/7/2.
 */
@Configuration
@ComponentScan(basePackages = {"txz.study.example"})
@PropertySource(value = {"classpath:application.properties"})
public class RabbitMQConfig {

    @Autowired
    private Environment env;

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(env.getProperty("mq.host").trim());
        factory.setPort(Integer.parseInt(env.getProperty("mq.port").trim()));
        factory.setUsername(env.getProperty("mq.username").trim());
        factory.setPassword(env.getProperty("mq.password").trim());
        factory.setVirtualHost(env.getProperty("mq.vhost"));
        return factory;
    }


    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory());
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(cachingConnectionFactory());
    }

    @Bean
    Queue queue() {
        String name = env.getProperty("mq.queue").trim();
        boolean durable = StringUtils.isNotBlank(env.getProperty("mq.queue.durable").trim()) ? Boolean.valueOf(env.getProperty("mq.queue.durable").trim()) : true;
        boolean exclusive = StringUtils.isNotBlank(env.getProperty("mq.queue.exclusive").trim()) ? Boolean.valueOf(env.getProperty("mq.queue.exclusive").trim()) : false;
        boolean autoDelete = StringUtils.isNotBlank(env.getProperty("mq.queue.autoDelete").trim()) ? Boolean.valueOf(env.getProperty("mq.queue.autoDelete").trim()) : false;
        return new Queue(name, durable, exclusive, autoDelete);
    }

    @Bean
    TopicExchange exchange() {
        String name = env.getProperty("mq.exchange");
        boolean durable = StringUtils.isNotBlank(env.getProperty("mq.exchange.durable").trim()) ? Boolean.valueOf(env.getProperty("mq.queue.durable").trim()) : true;
        boolean autoDelete = StringUtils.isNotBlank(env.getProperty("mq.exchange.autoDelete").trim()) ? Boolean.valueOf(env.getProperty("mq.queue.autoDelete").trim()) : false;
        return new TopicExchange(name, durable, autoDelete);
    }
    @Bean
    Binding binding() {
        String routekey = env.getProperty("mq.routekey");
        return BindingBuilder.bind(queue()).to(exchange()).with(routekey);
    }

}
