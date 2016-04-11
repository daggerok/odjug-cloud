package odjug.rest.template.client;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@RibbonClients
@SpringCloudApplication
public class RestTemplateClientSideLoadBalancer {

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateClientSideLoadBalancer.class, args);
    }

    //@Primary // in some cases might be needed for DNS URLs, like http://google.com/... not VIP
    @Bean(name = "restTemplate")
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @LoadBalanced
    @Bean(name = "loadBalancer")
    RestTemplate loadBalancer() {
        return new RestTemplate();
    }
}
