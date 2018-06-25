package txz.study.example.sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/6/25.
 */
public class Sender {
    //Set up the class and name the queue:
    private final static String QUEUE_NAME = "hello_taa";

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
        String message = "hello Rabbit";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("[x] sent " + message);

        // close the channel and the connection
        channel.close();
        connection.close();


    }
}
