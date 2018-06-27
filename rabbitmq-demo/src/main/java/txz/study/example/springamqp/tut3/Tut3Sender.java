package txz.study.example.springamqp.tut3;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by Administrator on 2018/6/27.
 */
public class Tut3Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanout;

    int dots = 0;
    int count = 0;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder sb = new StringBuilder();

        if (dots++ == 3) {
            dots = 1;
        }

        for (int i=0;i<dots;i++) {
            sb.append(".");
        }

        sb.append(Integer.toString(++count));
        String message = sb.toString();
        rabbitTemplate.convertAndSend(fanout.getName(), "", message);
        System.out.println("sent: "+message);
    }
}
