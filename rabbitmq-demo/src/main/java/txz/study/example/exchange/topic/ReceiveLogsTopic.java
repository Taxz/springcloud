package txz.study.example.exchange.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/6/26.
 */
public class ReceiveLogsTopic {
    private static final String TOPIC_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(TOPIC_NAME, BuiltinExchangeType.TOPIC);

        String queueName = channel.queueDeclare().getQueue();

        String[] arg = new String[]{"topic1.#"};
        for (String bindingKey : arg) {
            channel.queueBind(queueName, TOPIC_NAME, bindingKey);

        }

        System.out.println("waiting ......");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("receive:"+envelope.getRoutingKey()+":"+message);
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }
}
