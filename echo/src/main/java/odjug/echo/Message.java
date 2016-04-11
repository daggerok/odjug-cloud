package odjug.echo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/13/16.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor(staticName = "of")
public class Message implements Serializable {

    @NonNull
    String body;
}
