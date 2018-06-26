package txz.study.example.exchange.rpc;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/6/26.
 */
public class client {
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_name2";
    private String replyQueueName ;

    public client() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        replyQueueName = channel.queueDeclare().getQueue();

    }

    public String call(String message) throws IOException, InterruptedException {

        //唯一Id，做处理完响应判断
        final String corrId = UUID.randomUUID().toString();

        //响应队列和Id
        AMQP.BasicProperties properties = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        //发布消息
        channel.basicPublish("", requestQueueName, properties, message.getBytes("UTF-8"));

        //利用阻塞队列接受响应结果
        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

        //处理响应结果
        channel.basicConsume(replyQueueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                if (properties.getCorrelationId().equals(corrId)) {
                    response.offer(new String(body, "UTF-8"));
                }
            }
        });

        return response.take();
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    public static void main(String[] args) {

        client fibonaci = null;
        String reponse = null;
        try {
            fibonaci = new client();
            System.out.println("request fibonaci(30)");
            reponse = fibonaci.call("30");
            System.out.println("response : "+reponse);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (fibonaci != null)
            try {
                fibonaci.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
