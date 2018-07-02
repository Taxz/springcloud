package txz.study.example.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by Administrator on 2018/7/2.
 */

@Configuration
@PropertySource(value={"classpath:mail.properties"})
public class EmailConfig {

    @Autowired
    private Environment env;

    @Bean(name = "mailSender")
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("mail.host").trim());
//        mailSender.setPort(Integer.parseInt(env.getProperty("mail.port").trim()));
        mailSender.setUsername(env.getProperty("mail.username").trim());
        mailSender.setPassword(env.getProperty("mail.password").trim());
        mailSender.setDefaultEncoding("utf-8");
        // 配置邮件服务器
        Properties props = new Properties();
        // 让服务器进行认证,认证用户名和密码是否正确
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.timeout", "25000");
        mailSender.setJavaMailProperties(props);
        return mailSender;
    }
}
