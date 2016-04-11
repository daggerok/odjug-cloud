package odjug.stream;

import lombok.extern.slf4j.Slf4j;
import odjug.data.Domain;
import odjug.data.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/17/16.
 */
@Slf4j
@MessageEndpoint
public class Consumer {

    @Autowired
    DomainRepository domainRepository;

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void receive(Message<odjug.dto.Domain> domain) {

        odjug.dto.Domain payload = domain.getPayload();
        Domain entity = domainRepository.save(Domain.of(payload.getBody()));

        log.info("sink entity '{}'", entity);
    }
}
