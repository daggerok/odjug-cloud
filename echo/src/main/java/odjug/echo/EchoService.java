package odjug.echo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringCloudApplication
//@org.springframework.cloud.netflix.ribbon.RibbonClient("${spring.application.name}")
@org.springframework.cloud.netflix.feign.FeignClient("${spring.application.name}")
public class EchoService {

    public static void main(String[] args) {
        SpringApplication.run(EchoService.class, args);
    }

    @Bean
    public ExecutorService observableExecutor() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public ExecutorService completableExecutor() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        // fix rx.Observable: No serializer found for class rx.Observable
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }
}
