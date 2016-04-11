package odjug.spectator.counter;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.spectator.api.Registry;
import lombok.val;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/15/16.
 */
@RestController
public class CounterResourse {

    @Autowired
    Registry meters; // depends on: netflix contrib lib

    @RequestMapping(value = "/")
    @HystrixCommand(fallbackMethod = "countFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2500")
    })
    public Pair<String, Object> get() {

        val val = new Random().nextInt(9);

        if (0 == val % 2) throw new RuntimeException();

        meters.counter("get", "success", "true").increment();
        return Pair.of("success", meters.counter("get", "success", "true").count());
    }

    Pair<String, Object> countFallback(Throwable throwable) {

        meters.counter("get", "success", "false").increment();
        return Pair.of("fallbacks", meters.counter("get", "success", "false").count());
    }
}
