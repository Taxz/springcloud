package txz.study.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixDashoardApplication.class, args);
	}
}
