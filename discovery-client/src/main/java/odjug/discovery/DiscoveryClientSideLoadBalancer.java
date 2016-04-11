package odjug.discovery;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@SpringCloudApplication
public class DiscoveryClientSideLoadBalancer {

    @Autowired
    DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    String self;

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryClientSideLoadBalancer.class, args);
    }

    @RequestMapping(path = "/")
    public Map<String, Object> all() {

        Map<String, Object> map = map();

        map.put("services", discoveryClient.getServices());
        return map;
    }

    @RequestMapping(path = "/{name}")
    public Map<String, Object> local(@PathVariable("name") Optional<String> name) {

        Map<String, Object> map = map();

        name.ifPresent(n ->
                map.put(name.get().concat("Instance"),
                        discoveryClient.getInstances(n)));
        return map;
    }

    private Map<String, Object> map() {
        Map<String, Object> map = Maps.newHashMap();

        map.put("description", discoveryClient.description());
        map.put("local", discoveryClient.getLocalServiceInstance());
        return map;
    }
}
