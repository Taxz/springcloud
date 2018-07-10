package txz.study.example.pd1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Created by Administrator on 2018/7/10.
 */

//用来指定一个或多个定义了@Input或@Output注解的接口，以实现对消息通道的绑定，
//多个可通过 @EnableBinding(value = {sink.class,source.class})
//@EnableBinding(value = {Sink.class,SinkSender.class})
public class SinkReceiver {
    private static Logger logger = LoggerFactory.getLogger(SinkReceiver.class);

    //绑定了一个名为input的通道
    //不指定通道名，默认使用方法名
    //注册为数据流的事件监听器
    //@StreamListener(Sink.INPUT)
    public void receive(Object payload) {
        logger.info("Received:" + payload);
    }
}
