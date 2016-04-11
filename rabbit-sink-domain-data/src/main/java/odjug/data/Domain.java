package odjug.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/17/16.
 */
@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Domain implements Serializable {

    @Id
    @GeneratedValue

    Long id;
    @NonNull
    String body;
}
