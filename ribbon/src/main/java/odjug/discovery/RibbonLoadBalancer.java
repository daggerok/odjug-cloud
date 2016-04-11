package odjug.discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@SpringCloudApplication
@RibbonClient("${spring.application.name}")
@FeignClient("${spring.application.name}") // also applicable
public class RibbonLoadBalancer {

    @Autowired
    LoadBalancerClient loadBalancer;

    @Value("${spring.application.name}")
    String self;

    public static void main(String[] args) {
        SpringApplication.run(RibbonLoadBalancer.class, args);
    }

    @RequestMapping(path = "/ribbon/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ribbon(@PathVariable("name") Optional<String> name) {
        final ServiceInstance config = loadBalancer.choose(
                name.isPresent() ? name.get() : self);

        if (null == config) {
            return ":(";
        }
        return String.format("ribbon %s:\n%s\nsame: http://%s:%s",
                config.getServiceId(),
                config.getUri(),
                config.getHost(),
                config.getPort());
    }
}
