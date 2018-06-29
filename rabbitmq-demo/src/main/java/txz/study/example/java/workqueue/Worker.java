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
        //生产者可以不用队列
        /**
         * queue - the name of the queue
         * durable - true if we are declaring a durable queue (the queue will survive a server restart)
         * exclusive - true if we are declaring an exclusive queue (restricted to this connection) true：独占队列
         * autoDelete - true if we are declaring an autodelete queue (server will delete it when no longer in use)
         * arguments - other properties (construction arguments) for the queue
         */
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

        boolean autoack = false;
        //true 自动确认模式，消息发送后，认为成功并可删除, false 表示手动确认删除，
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
