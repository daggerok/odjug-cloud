package odjug.stream.rabbit.source.bind;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/18/16.
 */
public interface RabbitChannels {

    @Output
    MessageChannel output();
}
