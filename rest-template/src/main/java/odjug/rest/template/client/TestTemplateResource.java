package odjug.rest.template.client;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/16/16.
 */
@RestController
public class TestTemplateResource {

    //@Resource(name = "restTemplate")
    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("loadBalancer")
    //@Resource(name = "loadBalancer")
            RestTemplate loadBalancer;

    @RequestMapping("/")
    public Map<String, Object> all() {

        Map<String, Object> map = Maps.newHashMap();

        map.put("data", restTemplate.getForEntity("http://localhost:8761/eureka/apps", Map.class));
        return map;
    }

    @RequestMapping("/{name}")
    public Map<String, Object> rootOf(@PathVariable("name") Optional<String> name) {

        Map<String, Object> map = Maps.newHashMap();

        name.ifPresent(n ->
                map.put(n.concat(".data"), loadBalancer.getForEntity(
                        String.format("http://%s/", n), Map.class)));
        return map;
    }
}
