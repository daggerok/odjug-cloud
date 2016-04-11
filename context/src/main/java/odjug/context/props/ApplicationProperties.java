package odjug.context.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/14/16.
 */
@Data
@Component
@ConfigurationProperties(prefix = "props")
public class ApplicationProperties {

    String name;
    List<String> items;
}
