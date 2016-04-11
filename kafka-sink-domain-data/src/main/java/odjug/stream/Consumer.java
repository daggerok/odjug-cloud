package odjug.stream;

import lombok.extern.slf4j.Slf4j;
import odjug.data.Domain;
import odjug.data.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/17/16.
 */
@Slf4j
@Service
public class Consumer {

    @Autowired
    DomainRepository domainRepository;

    @StreamListener(Sink.INPUT)
    public void receive(Message<odjug.dto.Domain> domain) {

        odjug.dto.Domain payload = domain.getPayload();
        Domain entity = domainRepository.save(Domain.of(payload.getBody()));

        log.info("sink entity '{}'", entity);
    }
}
