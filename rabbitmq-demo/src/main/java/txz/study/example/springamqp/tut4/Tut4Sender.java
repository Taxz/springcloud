package txz.study.example.springamqp.tut4;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by Administrator on 2018/6/27.
 */
public class Tut4Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange exchange;

    private int index = 0;

    private int count = 0;

    private final String[] keys = {"orange", "black", "green"};

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder sb = new StringBuilder();

        if (++this.index == 3) {
            this.index = 0;
        }

        for(int i=0;i<=this.index;i++)
            sb.append(".");

        String key = keys[this.index];
        sb.append(key+" "+Integer.toBinaryString(++this.count));
        rabbitTemplate.convertAndSend(exchange.getName(), key, sb.toString());

        System.out.println(" sent: "+ sb.toString());

    }
}
