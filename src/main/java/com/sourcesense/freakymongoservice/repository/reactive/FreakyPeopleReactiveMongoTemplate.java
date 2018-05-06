package com.sourcesense.freakymongoservice.repository.reactive;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FreakyPeopleReactiveMongoTemplate {
	Flux<String> test();
}
