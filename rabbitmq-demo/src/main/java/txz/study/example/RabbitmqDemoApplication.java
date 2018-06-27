package txz.study.example;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import txz.study.example.springamqp.tut1.RabbitAmqpTutorialsRunner;

@EnableScheduling
@SpringBootApplication
public class RabbitmqDemoApplication {

	/**
	 * Interface used to indicate that a bean should <em>run</em> when it is contained within
	 * a {@link SpringApplication}. Multiple {@link CommandLineRunner} beans can be defined
	 * within the same application context and can be ordered using the {@link Ordered}
	 * interface or {@link Order @Order} annotation.
	 * <p>
	 * If you need access to {@link ApplicationArguments} instead of the raw String array
	 * consider using {@link ApplicationRunner}.
	 *
	 * @author Dave Syer
	 * @see ApplicationRunner
	 */
	@Profile("usage_message")
	@Bean
	public CommandLineRunner usage() {
		return strings -> {
            System.out.println("this app uses Spring Profiles to control its behavior.\n");
            System.out.println("Sample usage:java -jar rabbit-tutourials.jar --spring.profiles.active=hello-world,sender");

        };
	}

	@Profile("!usage_message")
	public CommandLineRunner tutorial() {
		return new RabbitAmqpTutorialsRunner();
	}


	public static void main(String[] args) {
		SpringApplication.run(RabbitmqDemoApplication.class, args);
	}
}
