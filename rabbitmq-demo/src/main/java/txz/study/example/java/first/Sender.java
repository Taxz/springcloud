package txz.study.example.java.first;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/6/25.
 */
public class Sender {

    //Set up the class and name the queue:
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
        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "hello Rabbit";
        /*
         *使用默认的交换器 AMQP default，路由key为队列名
         * with a routing key equal to the queue name
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("[x] sent " + message);

        // close the channel and the connection
        channel.close();
        connection.close();


    }
}
