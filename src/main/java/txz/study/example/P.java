package txz.study.example;

/**
 * Created by Administrator on 2018/6/21.
 */
public class P implements Runnable{
    private queue q;

    public P(queue q) {
        this.q=q;
    }


    @Override
    public void run() {
        for (int i= 0;i<100;i++) {
            q.puts("**"+i);
        }

    }
}
