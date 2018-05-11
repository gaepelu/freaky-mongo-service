package com.sourcesense.freakymongoservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sourcesense.freakymongoservice.configuration.SlowServiceConfiguration;
import com.sourcesense.freakymongoservice.datatype.FreakyPeople;
import com.sourcesense.freakymongoservice.datatype.client.RandomObject;
import com.sourcesense.freakymongoservice.exception.FreakyPeopleNotFoundException;
import com.sourcesense.freakymongoservice.exception.GenericException;
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

	/**
	 * Arricchisco l'oggetto FreakyPeople con i dati recuperati dal servizio /random del progetto freaky-slow-service
	 * Per ogni freaky people estratta effettuo l'arricchimento del dato
	 * 
	 * Sapendo che il servizio impiega 5s per elaborare il dato bisogna minimizzare i tempi di esecuzione e il consumo di risorse
	 */
	@Override
	public Flux<FreakyPeople> getFreakyPeopleExt(PageRequest pageable) {
		return freakyPeopleReactiveRepository.findPageable(pageable)
				.flatMapSequential(freakyPeople-> 
					webClient
						.get()
						.uri(slowServiceConfiguration.getRandomUrl())
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, clientResponse ->Mono.error(new RandomNotFoundException()))
			            .onStatus(HttpStatus::is5xxServerError, clientResponse ->Mono.error(new GenericException("E9999",clientResponse.bodyToMono(String.class).block())))
						.bodyToMono(RandomObject.class)
						.map(randomObject -> 
							freakyPeople
									.toBuilder()
									.favoriteUUID(randomObject.getRandomString())
									.favoriteNumber(randomObject.getRandomNumber())
									.build()
						)
						.switchIfEmpty(Mono.error(new RandomNotFoundException()))
				);
	}

	/**
	 * Estraggo un numero random iniziale
	 * Recupero tutte le freaky people e le arricchisco
	 * Il vincitore è il freaky people con il favorite number che si avvicina di più
	 */
	@Override
	public Mono<FreakyPeople> getFreakyWinner() {
		Mono<Double> magicNumber = webClient.get().uri(slowServiceConfiguration.getRandomSlowUrl()).retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new RandomNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,clientResponse -> Mono.error(new GenericException("E9999", clientResponse.bodyToMono(String.class).block())))
				.bodyToMono(RandomObject.class).map(random -> random.getRandomNumber())
				.switchIfEmpty(Mono.error(new RandomNotFoundException()));
		Mono<List<FreakyPeople>> freakyPeoples = freakyPeopleReactiveRepository.findAll()
				.flatMapSequential(freakyPeople-> 
					webClient
						.get()
						.uri(slowServiceConfiguration.getRandomUrl())
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, clientResponse ->Mono.error(new RandomNotFoundException()))
			            .onStatus(HttpStatus::is5xxServerError, clientResponse ->Mono.error(new GenericException("E9999",clientResponse.bodyToMono(String.class).block())))
						.bodyToMono(RandomObject.class)
						.map(randomObject -> 
							freakyPeople
									.toBuilder()
									.favoriteUUID(randomObject.getRandomString())
									.favoriteNumber(randomObject.getRandomNumber())
									.build()
						)
						.switchIfEmpty(Mono.error(new RandomNotFoundException()))
				)
				.switchIfEmpty(Flux.error(new FreakyPeopleNotFoundException()))
				.collectList();
		return Mono.zip(magicNumber, freakyPeoples, (n, lp) -> lp.stream()
				.map(p -> p.toBuilder().favoriteNumber(Math.abs(p.getFavoriteNumber() - n))
						.favoriteUUID(p.getFavoriteNumber() + "-" + n).build())
				.sorted((p1, p2) -> p1.getFavoriteNumber().compareTo(p2.getFavoriteNumber())).findFirst().orElseThrow(()->new FreakyPeopleNotFoundException()));
	}

}
