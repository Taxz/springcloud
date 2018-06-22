package txz.study.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/6/22.
 */

@RefreshScope
@RestController
public class TestControll {

    @Value("${from}")
    private String from;

    @RequestMapping("/from")
    public String getFrom() {
        return this.from;
    }
}
