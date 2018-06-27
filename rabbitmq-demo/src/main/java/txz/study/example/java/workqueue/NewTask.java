package txz.study.example.java.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/6/25.
 */
public class NewTask {

    public static String getMessage(String[] args) {
        if (args.length < 2) {
            return "hello world!";
        }

        return joinString(args," ");
    }

    public static String joinString(String[] args, String s) {

        int length = args.length;
        if (length == 0) {
            return "";
        }

        StringBuffer stringBuffer = new StringBuffer(args[0]);
        for (int i = 1; i < length; i++) {
            stringBuffer.append(s).append(args[i]);
        }
        return stringBuffer.toString();
    }

    private final static String QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        //create a connection to the server
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //持久化
        boolean durable = true;
        //declare a queue for us to send to; then we can publish a message to the queue:
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        String message = getMessage(new String[]{"Fifth message..............."});
        //持久化
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println("[x] sent " + message);

        // close the channel and the connection
        channel.close();
        connection.close();


    }
}
