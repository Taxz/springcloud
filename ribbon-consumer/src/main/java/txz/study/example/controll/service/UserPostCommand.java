package txz.study.example.controll.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2018/6/21.
 */
public class UserPostCommand extends HystrixCommand<String> {
    private RestTemplate template;

    protected UserPostCommand(RestTemplate template) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GROUPNAME")));
        this.template = template;

    }

    @Override
    protected String run() throws Exception {

        template.postForObject("http://HELLO-SERVICE/hello",new Object(),String.class);

        //刷新缓存，清理失效
       // UserCommand.flushCache(10L);

        return null;
    }
}
