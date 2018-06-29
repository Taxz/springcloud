package txz.study.example.springamqp.pulishconfirm;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

/**
 * Created by Administrator on 2018/6/27.
 */
public class ConfirmReceiver {

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    @RabbitHandler
    public void receiver1(String in, Channel channel, Message message) {
        receiver(in,channel,message,1);
    

    }


    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    @RabbitHandler
    public void receiver2(String in, Channel channel, Message message) {
        receiver(in,channel,message,2);
    }

    private void receiver(String in, Channel channel, Message message, int i) {
        System.out.println("收到消息："+in+"");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("consumer success");
        } catch (IOException e) {
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("fail");
            }
        }
    }


}
