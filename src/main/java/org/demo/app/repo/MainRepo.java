package org.demo.app.repo;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@NoRepositoryBean
public interface MainRepo<E> extends ReactiveCrudRepository<E, Long> {
}
