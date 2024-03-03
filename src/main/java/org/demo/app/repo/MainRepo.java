package org.demo.app.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MainRepo<E> extends ReactiveMongoRepository<E, String> {
}
