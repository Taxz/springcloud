package txz.study.example;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import txz.study.example.configure.FilterConfigure;


@EnableZuulProxy
@EnableConfigurationProperties({FilterConfigure.class})
@SpringCloudApplication
public class ApiGatewayDtnamicFilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayDtnamicFilterApplication.class, args);
	}

	@Bean
	public FilterLoader filterLoader(FilterConfigure filterConfigure) {
		FilterLoader filterLoader = FilterLoader.getInstance();
		filterLoader.setCompiler(new GroovyCompiler());
		try {
			FilterFileManager.setFilenameFilter(new GroovyFileFilter());
			FilterFileManager.init(filterConfigure.getInterval(),
					filterConfigure.getRoot() + "/pre",
					filterConfigure.getRoot() + "/post");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return filterLoader;
	}
}
