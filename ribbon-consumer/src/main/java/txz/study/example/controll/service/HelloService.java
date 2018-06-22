package txz.study.example.controll.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by Administrator on 2018/6/13.
 */
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(ignoreExceptions = {IOException.class},fallbackMethod = "fallback",commandKey = "helloService",groupKey = "hello",threadPoolKey = "helloServiceThreadPoolKey")
    public String helloService() {
        return restTemplate.getForEntity("http://HELLO-SERVICE/hello",String.class).getBody();
    }


    public String fallback(Throwable e) {
        System.out.println(e.getCause());
        return "系统升级中。。。。";
    }

    public String testExtends() {
        UserCommand u = new UserCommand(restTemplate);
        return u.execute();
    }
}
