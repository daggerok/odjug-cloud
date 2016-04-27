package odjug.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringCloudApplication
public class DiscoveryServiceRegistry {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceRegistry.class, args);
    }
}
