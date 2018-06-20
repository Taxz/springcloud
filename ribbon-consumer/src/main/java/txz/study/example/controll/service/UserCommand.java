package txz.study.example.controll.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2018/6/19.
 */
public class UserCommand extends HystrixCommand<String> {

    private RestTemplate template;

    protected UserCommand(RestTemplate template) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GROUPNAME")));
        this.template = template;
    }

    @Override
    protected String run() throws Exception {
        return template.getForEntity("http://HELLO-SERVICE/hello",String.class).getBody();
    }
}
