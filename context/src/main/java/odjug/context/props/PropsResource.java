package odjug.context.props;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/14/16.
 */
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//@RefreshScope // not necessary annotation in latest spring cloud versions
@RestController
public class PropsResource {

    @Autowired
    ApplicationProperties applicationProperties;

    @RequestMapping("/")
    public ApplicationProperties props() {
        return applicationProperties;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public void setProps(@RequestBody ApplicationProperties applicationProperties) {
        Optional.ofNullable(applicationProperties)
                .ifPresent(props -> this.applicationProperties = props);
    }
}
