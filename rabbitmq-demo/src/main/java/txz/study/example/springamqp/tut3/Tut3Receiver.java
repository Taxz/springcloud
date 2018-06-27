package txz.study.example.springamqp.tut3;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * Created by Administrator on 2018/6/27.
 */
public class Tut3Receiver {

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiver1(String in) {
        receiver(in,1);
    }


    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receiver2(String in) {
        receiver(in,2);
    }

    private void receiver(String in, int n) {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance: " + n + " receiver:" + in);

        doWork(in);

        watch.stop();
        System.out.println("instance: "+ n+ "done lost "+watch.getTotalTimeMillis()+" s");
    }

    private void doWork(String in) {
        for (char i : in.toCharArray()) {
            if (i == '.') {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
