package odjug.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/17/16.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Domain implements Serializable {

    Long id;

    @NonNull
    String body;
}
