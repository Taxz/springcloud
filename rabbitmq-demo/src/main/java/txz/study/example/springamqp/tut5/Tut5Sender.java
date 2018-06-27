package txz.study.example.springamqp.tut5;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by Administrator on 2018/6/27.
 */
public class Tut5Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    TopicExchange topicExchange;

    private int index;

    private int count;

    private final String[] kets = {"quick.orange.rabbit",
            "lazy.orange.elephant", "quick.orange.fox",
            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};

    @Scheduled(fixedDelay = 1000,initialDelay = 500)
    public void sent() {

        StringBuilder sb = new StringBuilder();
        sb.append("hello to");

        if (++this.index == kets.length) {
            this.index = 0;
        }

        String key = kets[index];
        sb.append(key).append(" ").append(Integer.toString(++this.count));
        rabbitTemplate.convertAndSend(topicExchange.getName(), key, sb.toString());
        System.out.println("sent : "+sb.toString());
    }
}
