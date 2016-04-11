package odjug.stream.rabbit.source.bind;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import odjug.dto.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Map;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/17/16.
 */
@Slf4j
@RestController
public class EdgeReaderGateway {

    static final String VIP = "rabbit-sink-domain-data";

    /*
    feign client
     */

    @FeignClient(VIP)
    interface DomainFiegnClient {

        @RequestMapping(path = "/api/domains", method = RequestMethod.GET)
        Resources<Domain> findAll();
    }

    @Autowired
    DomainFiegnClient client;

    @HystrixCommand(fallbackMethod = "fallbackView", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    @RequestMapping(path = "/proxy/domains")
    public Collection<Domain> alwaysFall() {

        return client.findAll().getContent();
    }

    /*
    client side load balanced rest template
     */

    @Autowired
    @Qualifier("loadBalancer")
    RestTemplate cloudRestTemplate;

    ParameterizedTypeReference<Resources<Domain>> type = new ParameterizedTypeReference<Resources<Domain>>() {};

    @HystrixCommand(fallbackMethod = "fallbackView")
    @RequestMapping(path = "/view/domains")
    public Collection<Domain> domainsView() {

        ResponseEntity<Resources<Domain>> response = cloudRestTemplate.exchange(
                String.format("http://%s/api/domains", VIP),
                HttpMethod.GET,
                null, // request body
                type);

        return response.getBody().getContent();
    }

    Collection<Domain> fallbackView() {

        return Lists.newArrayList();
    }

    @HystrixCommand(fallbackMethod = "fallbackMethod")
    @RequestMapping(path = "/domains")
    public Map domainsGteway() {

        return cloudRestTemplate.getForEntity(
                String.format("http://%s/api/domains", VIP), Map.class).getBody();
    }

    /*
    old style rest template
     */

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackMethod")
    @RequestMapping(path = "/oldSchool")
    public Map oldSchool() {

        ServiceInstance instance = discoveryClient.getInstances(VIP).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("no available domainData instance found"));

        return restTemplate.getForEntity(
                String.format("%s/api/domains", instance.getUri()),
                Map.class).getBody();
    }

    Map fallbackMethod() {

        return Maps.newHashMap();
    }
}
