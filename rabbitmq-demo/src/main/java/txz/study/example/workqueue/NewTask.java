package txz.study.example.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/6/25.
 */
public class NewTask {

    private static String getMessage(String[] args) {
        if (args.length < 1) {
            return "hello world!";
        }

        return joinString(args," ");
    }

    private static String joinString(String[] args, String s) {

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

        //declare a queue for us to send to; then we can publish a message to the queue:
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = getMessage(new String[]{"Fifth message..............."});
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("[x] sent " + message);

        // close the channel and the connection
        channel.close();
        connection.close();


    }
}
