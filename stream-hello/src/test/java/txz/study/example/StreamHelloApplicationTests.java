package txz.study.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import txz.study.example.pd1.SinkSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamHelloApplicationTests {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private SinkSender sinkSender;
	@Test
	public void contextLoads() {
		sinkSender.output().send(MessageBuilder.withPayload("From 123").build());
	}

}
