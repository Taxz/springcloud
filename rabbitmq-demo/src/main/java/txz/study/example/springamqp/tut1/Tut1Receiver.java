package txz.study.example.springamqp.tut1;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * Created by Administrator on 2018/6/27.
 */

@RabbitListener(queues = "hello_spring")
public class Tut1Receiver {

    @RabbitHandler
    public void received(String string) {
        System.out.println("received:"+string);
    }

}
