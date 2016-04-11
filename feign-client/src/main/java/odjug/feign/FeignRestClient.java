package odjug.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@EnableFeignClients
@SpringCloudApplication
public class FeignRestClient {

    /*
    feign will provide automatically implementation of feign-resource for you,
    so you don't have to use RestTemplate, i.e.:

    origin = restTemplate.getForObject("http://feign-resource/", Map.class);

    just use your interface (reign client), like so:

    origin =  action();
     */

    @FeignClient("feign-resource")
    interface FeignResourceClient {
        // you have to setup method while using FeignClient
        @RequestMapping(path = "/", method = RequestMethod.GET)
        Map<String, Object> action();
    }

    @Autowired
    FeignResourceClient client;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public Map<String, Object> gateway() {
        Map<String, Object> origin = client.action();

        return new HashMap<String, Object>() {{
            put("component", FeignResourceClient.class);
            putAll(origin);
        }};
    }

    public static void main(String[] args) {
        SpringApplication.run(FeignRestClient.class, args);
    }
}
