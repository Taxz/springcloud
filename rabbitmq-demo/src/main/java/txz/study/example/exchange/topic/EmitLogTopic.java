package txz.study.example.exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import txz.study.example.workqueue.NewTask;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/6/26.
 */
public class EmitLogTopic {
    private static final String TOPIC_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(TOPIC_NAME, BuiltinExchangeType.TOPIC);

        String routeKey = getRouting(new String[]{"topic1.routy.key"});
        String message = NewTask.getMessage(new String[]{});

        channel.basicPublish(TOPIC_NAME, routeKey, null, message.getBytes());

        channel.close();
        connection.close();
    }

    private static String getRouting(String[] strings){
        if (strings.length < 1)
            return "anonymous.info";
        return strings[0];
    }
}
