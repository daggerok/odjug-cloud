package odjug.spectator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.metrics.servo.ServoMetricsAutoConfiguration;

@SpringCloudApplication
@EnableAutoConfiguration(exclude = ServoMetricsAutoConfiguration.class)
public class SpectatorApplication {

    /*
    1. start config, discovery, spectator, hystrix-dashboard, zuul
    2. open dashboard home page http://dashboard/hystrix
    3. enter stream url: http://spectator/admin/hystrix/hystrix.stream
    4. run a few times load command:
        $ ab -n 10000 -c 3 -t 10 http://zuul/spectator/
       or
        $ ab -n 10000 -c 3 -t 10 http://spectator/
    5. monitor dashboard to see if circuit breaker was opened / closed
    6. check metrics here: http://zuul/spectator/admin/metrics
       or even like so: http://zuul/spectator/admin/metrics/get.*
     */

    public static void main(String[] args) {
        SpringApplication.run(SpectatorApplication.class, args);
    }
}
