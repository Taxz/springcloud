package txz.study.example.controll;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import txz.study.example.service.EmailService;

/**
 * Created by Administrator on 2018/7/2.
 */

@RestController("/v1/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(method = RequestMethod.POST)
    public JSONObject add(@RequestBody JSONObject jsonObject) {
          /* json结构体
        {
            "to":"xxx@163.com",
            "subject":"xxx",
            "text":"<html><head></head><body><h1>邮件测试</h1><p>hello!this is mail test。</p></body></html>"
        }
         */
        emailService.sendEmail(jsonObject.toJSONString());
        return jsonObject;
    }
}
