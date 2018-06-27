package txz.study.example.springamqp.rpc;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * Created by Administrator on 2018/6/27.
 */
public class Tut6Server {

    @RabbitListener(queues = "tut.rpc.requests")
    public int fibonacci(int n) {
        System.out.println("receiver: " + n);
        int result = fib(n);
        System.out.println("result : " + result);
        return result;
    }

    private int fib(int n) {
        return n == 0 ? 0 : n == 1 ? 1 : (fib(n - 1) + fib(n - 2));
    }
}
