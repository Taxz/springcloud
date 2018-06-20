package txz.study.example;

import com.netflix.zuul.FilterProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import txz.study.example.filter.AccessFilter;
import txz.study.example.filter.FilterProceess;


@EnableZuulProxy
@SpringBootApplication
public class ApiGatewayApplication {

	@Bean
	AccessFilter accessFilter() {
		return new AccessFilter();
	}


	//自定义路由映射规则
	@Bean
	public PatternServiceRouteMapper serviceRouteMapper() {
		return new PatternServiceRouteMapper(
				"(?<name>^.+)-(?<version>v.+$)", "${version}/${name}"
		);
	}
	@Bean
	public FilterProcessor processor() {
		FilterProcessor dd = new FilterProcessor();
		dd.setProcessor(new FilterProceess());
		return dd;
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
