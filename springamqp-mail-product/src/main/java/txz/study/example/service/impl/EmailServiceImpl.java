package txz.study.example.service.impl;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import txz.study.example.service.EmailService;

/**
 * Created by Administrator on 2018/7/2.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${mq.exchange}")
    private String exchange;

    @Value("${mq.routekey}")
    private String routekey;
    @Override
    public void sendEmail(String name) {
        try {
            rabbitTemplate.convertAndSend(exchange, routekey, name);
        } catch (Exception e) {
            logger.error("EmailServiceImpl "+ ExceptionUtils.getMessage(e));
        }
    }
}
