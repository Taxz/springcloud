package txz.study.example.controll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import txz.study.example.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

@RestController
public class Controll {

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String helle() {
        List<String> service = client.getServices();

        return "hello world,service:";
    }


    @RequestMapping(value = "/hello1",method = RequestMethod.GET)
    public String helle1(@RequestParam  String name) {

        return "hello "+name;
    }

    @RequestMapping(value = "/hello2",method = RequestMethod.POST)
    public String helles(@RequestBody User user) {

        return "hello "+user.getName();
    }


    @RequestMapping(value = "/local/hello")
    public String hh() {
        return "hello local";
    }

}
