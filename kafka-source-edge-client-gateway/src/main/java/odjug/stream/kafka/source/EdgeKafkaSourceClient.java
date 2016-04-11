package odjug.stream.kafka.source;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.web.client.RestTemplate;

@EnableZuulProxy
//@EnableCircuitBreaker or @EnableHystrix // already applied by @EnableZuulProxy
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableFeignClients
@SpringCloudApplication
@IntegrationComponentScan
@EnableBinding(Source.class)
public class EdgeKafkaSourceClient {

    /*
    1. start docker kafka (see README.md)
    2. start config, discovery, kafka-sink-domain-data and kafka-source-edge-client-gateway
    3. curl localhost:8000/domains | pj
    4. curl -d holla localhost:8000/domain
    5. curl localhost:8000/domains | pj

    eventual consistency:
    curl -d '{"body":"content"}' -H 'content-type:application/json' localhost:8000/kafka-sink-domain-data/api/domains

    avoid message broker:
    curl -XPOST -d '{"body":"content"}' -H 'content-type:application/json' localhost:8000/domain
     */

    /*
    zipkin use cases:
        - is my app ok last hour-two?
        - not for collection, but for comprehension (avg request duration, data flow)
        - do not trace all request, not more 10% of them will be enough to fibd the ptroblem

    1. to main build.gradle add needed repo:

        maven { url 'https://repo.spring.io/libs-milestone' }

    2. add needed deps for both kafka-sink-domain-data and kafka-source-edge-client-gateway:

        compile 'org.springframework.cloud:spring-cloud-starter-sleuth'
        compile 'org.springframework.cloud:spring-cloud-starter-zipkin'

    3. install docker
    4. start config, discovery, kafka-sink-domain-data and kafka-source-edge-client-gateway
    5. checkout docker-zipkin:

        git clone https://github.com/openzipkin/docker-zipkin && cd docker-zipkin/

    5. start docker-zipkin:

        docker-compose -f docker-compose-legacy.yml up

    6. make a few and push few posts throw zuul proxy and message broker, just like so:

        curl -XPOST -d '{"body":"some content"}' -H 'content-type:application/json' localhost:8000/domain

    7. open zipkin UI and verify if information appears
     */

    @Bean(name = "restTemplate")
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @LoadBalanced
    @Bean(name = "loadBalancer")
    RestTemplate loadBalancer() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EdgeKafkaSourceClient.class, args);
    }
}
