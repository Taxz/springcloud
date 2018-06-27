package txz.study.example.springamqp.rpc;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by Administrator on 2018/6/27.
 */
public class Tut6Client {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange exchange;

    int start = 0;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {

        System.out.println("sent fibonaci start " + start);
        Integer result = (Integer) template.convertSendAndReceive(exchange.getName(), "rpc", start++);
        System.out.println("response     :" + result);
    }

}
