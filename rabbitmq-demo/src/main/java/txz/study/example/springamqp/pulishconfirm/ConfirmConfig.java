package txz.study.example.springamqp.pulishconfirm;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Administrator on 2018/6/27.
 */
@Profile({"tut3", "pub-sub", "publish-subscribe"})
@Configuration
public class ConfirmConfig {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }

    @Profile("receiver")
    public static class ReceiverConfig{

        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1(FanoutExchange fanout, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
        }

        @Bean
        public Binding binding2(FanoutExchange fanout, Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(fanout);
        }

        @Bean
        public ConfirmReceiver receiver() {
            return new ConfirmReceiver();
        }
    }

    @Profile("sender")
    @Bean
    public ConfirmSender sender() {
        return new ConfirmSender();
    }
}
