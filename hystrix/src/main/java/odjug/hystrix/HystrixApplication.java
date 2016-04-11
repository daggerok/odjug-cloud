package odjug.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
//@EnableHystrix // @org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
//@EnableDiscoveryClient
//@SpringBootApplication
public class HystrixApplication {

    /*
    1. start config, discovery, hystrix-dashboard and hystrix
    2. go to http://dasboard/hystrix
    3. enter http://hystrix/admin/hystrix.stream to monitor
    4. run few times terminal command: ab -n 10000 http://hystrix/you/can/always/make/me/smile
    5. watching on hystrix-dashboard: circuit open and close
     */

    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class, args);
    }
}
