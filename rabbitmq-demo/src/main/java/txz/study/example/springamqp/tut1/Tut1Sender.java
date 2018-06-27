package txz.study.example.springamqp.tut1;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by Administrator on 2018/6/27.
 */
public class Tut1Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    @Scheduled(fixedDelay = 1000,initialDelay = 500)
    public void send() {
        String message = "Hello world from springamqp";
        this.rabbitTemplate.convertAndSend(queue.getName(), message);
        System.out.println("sent " + message);
    }
}
