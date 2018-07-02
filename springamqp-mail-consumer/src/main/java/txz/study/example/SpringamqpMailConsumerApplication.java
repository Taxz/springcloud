package txz.study.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"txz.study.example"})
@SpringBootApplication
public class SpringamqpMailConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringamqpMailConsumerApplication.class, args);
	}
}
