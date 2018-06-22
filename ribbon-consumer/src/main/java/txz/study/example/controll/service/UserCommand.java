package txz.study.example.controll.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;

import static jdk.nashorn.internal.ir.FunctionNode.Kind.GETTER;

/**
 * Created by Administrator on 2018/6/19.
 */
public class UserCommand extends HystrixCommand<String> {
    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("CommandKey");

    private RestTemplate template;
    private Long id;

    protected UserCommand(RestTemplate template) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GROUPNAME")).andCommandKey(GETTER_KEY));
        this.template = template;
    }

    @Override
    protected String run() throws Exception {
        return template.getForEntity("http://HELLO-SERVICE/hello",String.class).getBody();
    }

  /*  //开启请求缓存
    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }


    //重写刷新缓存
    public static void flushCache(Long id) {
        HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(String.valueOf(id));

    }*/
}
