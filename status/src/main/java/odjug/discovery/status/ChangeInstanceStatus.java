package odjug.discovery.status;

import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EurekaStatusChangedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringCloudApplication
public class ChangeInstanceStatus {

    @Bean
    CommandLineRunner runner() {
        return args -> log.info("possible statues: {}",
                Stream.of(InstanceInfo.InstanceStatus.values())
                        .map(Enum::name).collect(Collectors.toList()));
    }

    @EventListener
    public void onInstanceStatusChange(EurekaStatusChangedEvent event) {
        log.info("status changed on {}", event.getStatus());

        if (event.getStatus().equals(InstanceInfo.InstanceStatus.OUT_OF_SERVICE)) {
            log.info("i'm off :(");
        }
    }

    /*
    eureka rest api

    1. show all:

    curl -H 'accept:application/json' localhost:8761/eureka/apps | pj

    2. show only specify application instances:

    curl -H 'accept:application/json' localhost:8761/eureka/apps/status | pj

    3. drop (update sync status) concrete instance:

    curl -XPUT localhost:8761/eureka/apps/${app}/${instanceId}/status?value=OUT_OF_SERVICE

    for example

    curl -XPUT localhost:8761/eureka/apps/status/test_node/status?value=OUT_OF_SERVICE

    wait for a minute and verify application logs:

    o.discovery.status.ChangeInstanceStatus  : status changed on OUT_OF_SERVICE
    o.discovery.status.ChangeInstanceStatus  : i'm off :(

    now anybody who uses ribbon or some other client load balancer will not be able to find this guy,
    so we can easily test this instance in out cluster
     */

    /*
    also please look on actuator configprops endpoint

    hrrp://vip/admin/configprops

    find HealthIndicator, you can override it with needed information, like so

    @Bean
    public HealthIndicator() {
      return () -> Health.status("customize it").build() // or return new HealthIndicator { ... };
    }
     */

    public static void main(String[] args) {
        SpringApplication.run(ChangeInstanceStatus.class, args);
    }
}
