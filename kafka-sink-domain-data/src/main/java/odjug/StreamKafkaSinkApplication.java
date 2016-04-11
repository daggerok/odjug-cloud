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
public class StreamKafkaSinkApplication {

    /*
    don't understand how fix eventual consistency using kafka
    kafka was designed more as produce-centric, but not consume-centric as rabbitmq
    so right now, if you need durable message delivery try use rabbitmq for it, of try figure out this issue

    problem also can be with docker container configuration /tmp zk data dir
    here is a link:

        http://stackoverflow.com/questions/32537943/how-to-create-a-durable-topic-in-kafka
     */

    @Bean
    CommandLineRunner runner(DomainRepository domainRepository) {

        return args -> domainRepository.save(
                Stream.of("one", "two", "three")
                        .map(Domain::of)
                        .collect(Collectors.toList()));
    }

    public static void main(String[] args) {
        SpringApplication.run(StreamKafkaSinkApplication.class, args);
    }
}
