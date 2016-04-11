package odjug.stream.kafka.sink;

import odjug.StreamRabbitSinkApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.annotation.DirtiesContext;

//@DirtiesContext
//@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StreamRabbitSinkApplication.class)
public class StreamKafkaSourceApplicationTests {

    @Autowired
    Sink sink;

    @Ignore // because of broker
    @Test
    public void contextLoads() {
        assertNotNull(sink);
        assertNotNull(sink.input());
    }
}
