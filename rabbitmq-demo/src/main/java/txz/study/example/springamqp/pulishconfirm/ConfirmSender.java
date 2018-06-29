package txz.study.example.springamqp.pulishconfirm;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by Administrator on 2018/6/27.
 */
public class ConfirmSender implements RabbitTemplate.ReturnCallback{

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

        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println(" message send fail!,casue:" + cause + " correlationData :" + correlationData);
            }else {
                System.out.println("send success!");
            }
        }));

        rabbitTemplate.convertAndSend(fanout.getName(),message);
        System.out.println("sent: "+message);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("send return success: " + message.toString() + " replyText: " + replyText + " exchange: " + exchange + " routingkey: " + routingKey);
    }
}
