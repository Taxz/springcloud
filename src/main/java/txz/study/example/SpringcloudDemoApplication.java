package txz.study.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SpringcloudDemoApplication {

	public static void main(String[] args) {
		queue q = new queue();
		C c = new C(q);
		P p = new P(q);
		new Thread(c).start();
		new Thread(p).start();
		new Thread(c).start();
		new Thread(p).start();
	}
}
