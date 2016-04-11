package odjug.zuul;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

//import org.springframework.boot.test.WebIntegrationTest;

@WebAppConfiguration
//@WebIntegrationTest(randomPort = true)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZuulApplication.class)
public class ZuulApplicationTests {

    @Test
    public void contextLoads() {}
}
