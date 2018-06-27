package txz.study.example.springamqp.tut2;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * Created by Administrator on 2018/6/27.
 */
@RabbitListener(queues = "hello_spring")
public class Tut2Receiver {
    private final int instance;

    public Tut2Receiver(int instance) {
        this.instance = instance;
    }

    @RabbitHandler
    public void recevied(String in) throws InterruptedException {
        /**
         * Simple stop watch, allowing for timing of a number of tasks,
         * exposing total running time and running time for each named task.
         *
         * <p>Conceals use of {@code System.currentTimeMillis()}, improving the
         * readability of application code and reducing the likelihood of calculation errors.
         *
         * <p>Note that this object is not designed to be thread-safe and does not
         * use synchronization.
         *
         * <p>This class is normally used to verify performance during proof-of-concepts
         * and in development, rather than as part of production applications.
         *
         */
        StopWatch sw = new StopWatch();
        sw.start();

        System.out.println("instance " + this.instance + " receive:" + in);
        doWork(in);
        sw.stop();

        System.out.println("instance :" + this.instance + " spend: " + sw.getTotalTimeMillis() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        for (char i : in.toCharArray()) {
            if (i == ',')
                Thread.sleep(1000);

        }
    }
}
