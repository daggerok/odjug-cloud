package odjug.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/17/16.
 */
@RepositoryRestResource
public interface DomainRepository extends JpaRepository<Domain, Long> {}
