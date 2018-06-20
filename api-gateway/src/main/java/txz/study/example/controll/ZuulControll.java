package txz.study.example.controll;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/6/13.
 */

@RestController
public class ZuulControll {
    @RequestMapping(value = "/local/hello")
    public String hh() {
        return "hello local";
    }
}
