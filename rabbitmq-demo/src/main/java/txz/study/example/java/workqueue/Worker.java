package txz.study.example.java.workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/6/25.
 */
public class Worker {
    private final static String QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //持久化
        boolean durable = true;

        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        //处理完上一条信息，才会发送新信息；
        channel.basicQos(1);

        System.out.println("[*] Waiting for message To exit Ctri+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("recevice message:" + message);
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[x] Done");
                    //积极确认
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        //默认为false，开启手动确认模式，true，表示关闭，
        boolean autoack = false;
        channel.basicConsume(QUEUE_NAME, autoack, consumer);
    }

    private static void doWork(String message) throws InterruptedException {
        for (char ch : message.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);

            }
        }
    }
}
