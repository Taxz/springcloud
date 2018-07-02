package txz.study.example.springamqp.rpc;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Administrator on 2018/6/27.
 */
@Profile({"tut6", "rpc"})
@Configuration
public class Tut6Config {

    @Profile("client")
    private static class ClientConfig{

        @Bean
        public DirectExchange directExchange() {
            return new DirectExchange("tut.rpc");
        }

        @Bean
        public Tut6Client client() {
            return new Tut6Client();
        }
    }

    @Profile("server")
    private static class ServerConfig {
        //Exchange(tut.rpc) <---    RouteKey(rpc)    ---> Queue(tut.rpc.requests)
        @Bean
        public Queue queue() {
            return new Queue("tut.rpc.requests");
        }

        @Bean
        public DirectExchange exchange() {
            return new DirectExchange("tut.rpc");
        }

        @Bean
        public Binding binding(DirectExchange exchange,Queue queue) {
            return BindingBuilder.bind(queue).to(exchange).with("rpc");
        }
    }

    @Bean
    public Tut6Server server() {
        return new Tut6Server();
    }

}
