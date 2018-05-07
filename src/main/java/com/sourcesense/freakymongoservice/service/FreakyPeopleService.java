package com.sourcesense.freakymongoservice.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sourcesense.freakymongoservice.datatype.FreakyPeople;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FreakyPeopleService {

	Mono<FreakyPeople> saveFreakyPeople(FreakyPeople freakyPeople);

	Mono<FreakyPeople> getFreakyPeopleById(String id);

	Flux<FreakyPeople> getFreakyPeople(Pageable pageable);

	Mono<Void> deleteById(String id);

	Flux<FreakyPeople> getFreakyPeopleExt(PageRequest of);

}
