package txz.study.example.springamqp.tut5;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;


/**
 * Created by Administrator on 2018/6/27.
 */
public class Tut5Receiver {

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String string) {
        receiver(1,string);
    }


    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String string) {
        receiver(2,string);
    }

    public void receiver(int i, String in) {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance: " + i + "receiver: " + in);
        try {
            Thread.sleep(1300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        watch.stop();
        System.out.println("instance: " + i + "cost " + watch.getTotalTimeMillis() + "s");
    }
}
