package com.sourcesense.freakymongoservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sourcesense.freakymongoservice.configuration.SlowServiceConfiguration;
import com.sourcesense.freakymongoservice.datatype.FreakyPeople;
import com.sourcesense.freakymongoservice.datatype.client.RandomObject;
import com.sourcesense.freakymongoservice.exception.RandomNotFoundException;
import com.sourcesense.freakymongoservice.repository.reactive.FreakyPeopleReactiveRepository;
import com.sourcesense.freakymongoservice.service.FreakyPeopleService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FreakyPeopleServiceImpl implements FreakyPeopleService {

	@Autowired
	private FreakyPeopleReactiveRepository freakyPeopleReactiveRepository;
	
	@Autowired
	private SlowServiceConfiguration slowServiceConfiguration;
	
	private final WebClient webClient;
	
	public FreakyPeopleServiceImpl() {
		this.webClient = WebClient.builder().build();
	}
	
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

	@Override
	public Flux<FreakyPeople> getFreakyPeopleExt(PageRequest pageable) {
		return freakyPeopleReactiveRepository.findPageable(pageable)
				.flatMapSequential(freakyPeople-> 
					webClient
						.get()
						.uri(slowServiceConfiguration.getRandomUrl())
						.retrieve()
						.bodyToMono(RandomObject.class)
						.map(randomObject -> {
							freakyPeople.setFavoriteUUID(randomObject.getRandomString());
							freakyPeople.setFavoriteNumber(randomObject.getRandomNumber());
							return freakyPeople;
						})
						.switchIfEmpty(Mono.error(new RandomNotFoundException()))
				);
	}

}
