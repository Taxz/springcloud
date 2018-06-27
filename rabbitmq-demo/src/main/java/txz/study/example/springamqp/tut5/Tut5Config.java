package txz.study.example.springamqp.tut5;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Administrator on 2018/6/27.
 */

@Profile({"tut5", "topic"})
@Configuration
public class Tut5Config {

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("tut.topic");
    }

    public static class ReceiverConfig {

        @Bean
        public Tut5Receiver receiver() {
            return new Tut5Receiver();
        }

        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding bindingla(TopicExchange topic,Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(topic).with("*.orange.*");
        }

        @Bean
        public Binding bindinglb(TopicExchange topic,Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(topic).with("**.*.rabbit");
        }

        @Bean
        public Binding binding2a(TopicExchange topic,Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(topic).with("lazy.#");
        }


    }

    @Bean
    public Tut5Sender sender() {
        return new Tut5Sender();
    }

}
