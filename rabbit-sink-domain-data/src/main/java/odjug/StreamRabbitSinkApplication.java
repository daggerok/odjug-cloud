package odjug;

import odjug.data.Domain;
import odjug.data.DomainRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringCloudApplication
@EnableBinding(Sink.class)
public class StreamRabbitSinkApplication {

    @Bean
    CommandLineRunner runner(DomainRepository domainRepository) {

        return args -> domainRepository.save(
                Stream.of("one", "two", "three")
                        .map(Domain::of)
                        .collect(Collectors.toList()));
    }

    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitSinkApplication.class, args);
    }
}
