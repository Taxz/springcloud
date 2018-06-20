package txz.study.example.controll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import txz.study.example.service.HelloService;
import txz.study.example.service.User;

/**
 * Created by Administrator on 2018/6/13.
 */
@RestController
public class FeignControll {

    @Autowired
    HelloService helloService;

    @RequestMapping(value = "/feign-consumer", method = RequestMethod.GET)
    public String hello() {

        return helloService.hello();
    }

    @RequestMapping(value = "/feign-consumer2", method = RequestMethod.GET)
    public String hellos() {
        User user = new User();
        user.setAge("12");
        user.setName("585");
        return helloService.hello12("zhangsan")+helloService.hello1(user);
    }

}
