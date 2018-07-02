package txz.study.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@ComponentScan(basePackages = {"txz.study.example"})
@SpringBootApplication
public class SpringamqpMailProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringamqpMailProductApplication.class, args);
	}
}
