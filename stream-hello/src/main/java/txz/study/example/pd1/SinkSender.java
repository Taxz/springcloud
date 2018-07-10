package txz.study.example.pd1;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.MessageChannel;

/**
 * Created by Administrator on 2018/7/10.
 */

public interface SinkSender {

    //@Output(Sink.INPUT)
    MessageChannel output();
}
