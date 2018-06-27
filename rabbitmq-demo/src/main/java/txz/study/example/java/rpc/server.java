package txz.study.example.java.rpc;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/6/26.
 */
public class server {
    private static String requestQueueName = "rpc_name2";

    private static int fib(int b) {
        if (b == 1)
            return 1;

        if( b == 0)
            return 0;

        return fib(b - 1) + fib(b - 2);
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //队列绑定
        channel.queueDeclare(requestQueueName, false, false, false, null);

        //处理完上一条消息，才会发送新消息
        channel.basicQos(1);

        System.out.println("wait .....");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                AMQP.BasicProperties properties1 = new AMQP.BasicProperties.Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();

                String response = "";

                try{
                    String message = new String(body, "UTF-8");
                    int num = Integer.valueOf(message);
                    System.out.println("receive message:" + num + " starte fibonaci");
                    response += fib(num);
                }finally {

                    //返回处理结果
                    channel.basicPublish("", properties.getReplyTo(), properties1, response.getBytes("UTF-8"));

                    //发送消息处理完确认
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    synchronized (this) {
                        this.notify();
                    }
                }


            }
        };

        channel.basicConsume(requestQueueName, false, consumer);

        while (true) {
            synchronized (consumer) {
                try {
                    consumer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (connection !=null)
                        connection.close();

                }
            }
        }
    }
}
