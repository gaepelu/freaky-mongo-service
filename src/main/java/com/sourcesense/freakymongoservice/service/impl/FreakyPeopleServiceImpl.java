package com.sourcesense.freakymongoservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sourcesense.freakymongoservice.datatype.FreakyPeople;
import com.sourcesense.freakymongoservice.repository.reactive.FreakyPeopleReactiveRepository;
import com.sourcesense.freakymongoservice.service.FreakyPeopleService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FreakyPeopleServiceImpl implements FreakyPeopleService {

	@Autowired
	private FreakyPeopleReactiveRepository freakyPeopleReactiveRepository;
	
	@Override
	public Mono<FreakyPeople> saveFreakyPeople(FreakyPeople freakyPeople) {
		return freakyPeopleReactiveRepository.save(freakyPeople);
	}

	@Override
	public Mono<FreakyPeople> getFreakyPeopleById(String id) {
		return freakyPeopleReactiveRepository.findOneById(id);
	}

	@Override
	public Flux<FreakyPeople> getFreakyPeople(Pageable pageable) {
		return freakyPeopleReactiveRepository.findPageable(pageable);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return freakyPeopleReactiveRepository.deleteById(id);
	}

}
