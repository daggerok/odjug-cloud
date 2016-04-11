package odjug.stream.rabbit.source;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;

//import org.springframework.test.annotation.DirtiesContext;

//@DirtiesContext
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EdgeRabbitSourceClient.class)
public class StreamRabbitSourceApplicationTests {

    @Autowired
    Source source;

    @Ignore // because of broker
    @Test
    public void contextLoads() {
        assertNotNull(source);
        assertNotNull(source.output());
    }
}
