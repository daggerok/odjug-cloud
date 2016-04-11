package odjug.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@SpringCloudApplication
public class FeignResource {

    @Autowired
    DiscoveryClient client;

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map action() {
        final ServiceInstance localInstance = client.getLocalServiceInstance();

        return new HashMap<String, Object>() {{
            put("instance", localInstance);
        }};
    }

    public static void main(String[] args) {
        SpringApplication.run(FeignResource.class, args);
    }
}
