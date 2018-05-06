package com.sourcesense.freakymongoservice.repository.reactive;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.sourcesense.freakymongoservice.datatype.FreakyPeople;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FreakyPeopleReactiveRepository extends ReactiveMongoRepository<FreakyPeople, String> {

	Mono<FreakyPeople> findOneById(String id);

	@Query("{}")
	Flux<FreakyPeople> findPageable(Pageable pageable);

}
