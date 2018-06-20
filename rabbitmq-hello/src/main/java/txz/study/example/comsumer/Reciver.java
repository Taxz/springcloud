package txz.study.example.comsumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/6/14.
 */
@Component
@RabbitListener(queues = "hello")
public class Reciver {

    @RabbitHandler
    public void proccess(String hello) {
        System.out.println("Recive:"+hello);
    }


}
