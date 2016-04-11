package odjug.hystrix.lifeisgood;

import com.google.common.collect.Sets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/15/16.
 */
@Slf4j
@RestController
public class NiceResource {
    @SneakyThrows
    @RequestMapping("/you/can/always/make/me/smile")
    @HystrixCommand(fallbackMethod = "stillHappy", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2500")
    })
    public Set<String> happy() {
        long timeout = new Random().nextInt(2);

        TimeUnit.SECONDS.sleep(new Random(timeout).nextLong());
        log.info("process spent time: {}", timeout);

        if (timeout < 1) return Sets.newHashSet("happy", "very", "nice");

        throw new RuntimeException("you will never see this");
    }

    Set<String> stillHappy() {
        return Sets.newHashSet("good", "for", "you");
    }
}
