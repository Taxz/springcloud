package txz.study.example.exchange.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/6/26.
 */
public class ReceiveLogsDirect {
    private static String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String queueName = channel.queueDeclare().getQueue();

        String[] arg = new String[]{"info","warning","error"};
        for (String servertity : arg) {
            channel.queueBind(queueName, EXCHANGE_NAME, servertity);

        }

        System.out.println("wait......");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String message = new String(body, "UTF-8");
                System.out.println("[x] Receiverd: " + envelope.getRoutingKey() + ":" + message);
            }
        };

        channel.basicConsume(queueName,true,consumer);
    }
}
