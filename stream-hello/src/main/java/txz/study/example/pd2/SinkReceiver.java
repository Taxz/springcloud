package txz.study.example.pd2;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Created by Administrator on 2018/7/10.
 */

@EnableBinding(value = {Sink.class})
public class SinkReceiver {


}
