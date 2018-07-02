package txz.study.example.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import txz.study.example.component.MailMessageListenerAdapter;

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
        factory.setPort(Integer.valueOf(env.getProperty("mq.port").trim()));
        factory.setVirtualHost(env.getProperty("mq.vhost").trim());
        factory.setUsername(env.getProperty("mq.username").trim());
        factory.setPassword(env.getProperty("mq.password").trim());
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
    public SimpleMessageListenerContainer listenerContainer(@Qualifier("mailMessageListenerAdapter") MailMessageListenerAdapter mailMessageListenerAdapter) {
        String queueNane = env.getProperty("mq.queue").trim();
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(cachingConnectionFactory());
        simpleMessageListenerContainer.setQueueNames(queueNane);
        simpleMessageListenerContainer.setMessageListener(mailMessageListenerAdapter);

        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return simpleMessageListenerContainer;

    }
}
