package odjug.config;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringCloudApplication
//@org.springframework.cloud.netflix.ribbon.RibbonClient("${spring.application.name}")
//@org.springframework.cloud.netflix.feign.FeignClient("${spring.application.name}")
public class ConfigServer {

    /*
    curl -H 'accept:application/json' localhost:8888/config/default | pj
    ...
    "propertySources": [
        {
        "name": "https://github.com/daggerok/odjug-cloud-config/config.yml",
        ...
        },
        {
        "name": "https://github.com/daggerok/odjug-cloud-config/application.yml",
        ...
        }
     ...

     also applicable (see: http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html#_quick_start):
        - json:
            open http://localhost:8888/config/master | pj
        - raw YAML:
            http://localhost:8888/master/default-config.yml
     */

    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class, args);
    }
}
