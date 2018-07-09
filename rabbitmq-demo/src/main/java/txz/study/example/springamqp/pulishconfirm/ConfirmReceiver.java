package txz.study.example.springamqp.pulishconfirm;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;

import java.io.IOException;

/**
 * Created by Administrator on 2018/6/27.
 */


@RabbitListener(queues = "hello")
public class ConfirmReceiver {


    @RabbitHandler
    private void receiver(String in, Channel channel, Message message, int i) {
        System.out.println("队列1收到消息："+in+"");
        /*AMQP.BasicProperties properties1 = new AMQP.BasicProperties.Builder()
                .build();*/
        try {
            String result = "success";
            //channel.basicPublish("ABExchange", "",properties1, result.getBytes("UTF-8"));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
            System.out.println("consumer success");
        } catch (IOException e) {
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("fail");
            }
        }
    }


}
