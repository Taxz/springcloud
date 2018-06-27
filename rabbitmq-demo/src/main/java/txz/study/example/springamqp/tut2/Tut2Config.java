package txz.study.example.springamqp.tut2;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Administrator on 2018/6/27.
 */

@Profile({"tut2", "work-queues"})
@Configuration
public class Tut2Config {

    @Bean
    public Queue queue() {
        return new Queue("hello_spring");
    }

    @Profile("receiver")
    public static class ReceivedConfig {

        @Bean
        public Tut2Receiver receiver() {
            return new Tut2Receiver(1);
        }

        @Bean
        public Tut2Receiver receiver1() {
            return new Tut2Receiver(2);
        }

    }

    @Profile("sender")
    @Bean
    public Tut2Sender sender() {

        return new Tut2Sender();
    }
}
