package odjug.stream.kafka.source.bind;

import lombok.extern.slf4j.Slf4j;
import odjug.dto.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/17/16.
 */
@Slf4j
@RestController
public class EdgeWriterGateway {

    /*
    spring cloud stream
     */
/*
    // we don't need create a channel adapter as in regular spring-integration project
    @Autowired
    MessageChannel output;
*/
    @Autowired DomainWriter writer;

    @RequestMapping(path = "/domain", method = RequestMethod.POST)
    public void sendMessage(@RequestBody String message) {

//        output.send(MessageBuilder.withPayload(Domain.of(message)).build());
        writer.write(MessageBuilder.withPayload(Domain.of(message)).build());
    }
}

@MessagingGateway
interface DomainWriter {

    @Gateway(requestChannel = Source.OUTPUT)
    void write(Message message);
}
