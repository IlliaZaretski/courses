package com.example.courses.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CourseReactiveRepository extends ReactiveMongoRepository<MongoCourse, String> {

    Flux<MongoCourse> findByTitle(String title);
}
