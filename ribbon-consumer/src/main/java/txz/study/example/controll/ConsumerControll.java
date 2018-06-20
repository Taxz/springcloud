package txz.study.example.controll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import txz.study.example.controll.service.HelloService;
import txz.study.example.controll.service.UserCommand;

/**
 * Created by Administrator on 2018/6/13.
 */
@RestController
public class ConsumerControll {

    @Autowired
    HelloService service;

    @RequestMapping("/ribbon-consume")
    public String helloConsumer() {

        return service.helloService();
    }

    @RequestMapping("/hello")
    public String testEx() {
        return service.testExtends();
    }

}

