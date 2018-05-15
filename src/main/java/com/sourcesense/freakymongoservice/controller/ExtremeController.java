package com.sourcesense.freakymongoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.sourcesense.freakymongoservice.configuration.SlowServiceConfiguration;
import com.sourcesense.freakymongoservice.datatype.client.RandomObject;
import com.sourcesense.freakymongoservice.exception.GenericException;
import com.sourcesense.freakymongoservice.exception.RandomNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ExtremeController {
	
	@Autowired
	private SlowServiceConfiguration slowServiceConfiguration;
	
	@GetMapping(value="extreme/{num}",produces="application/stream+json")
	public Flux<Double> extrebeByNum(@PathVariable(name="num",required=true) Integer num){
		//Creo il client
		WebClient webClient = WebClient.builder().build();
		return Flux.range(1, num).flatMap(n->
			webClient
				.get()
				.uri(slowServiceConfiguration.getRandomUrl())
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse ->Mono.error(new RandomNotFoundException()))
		        .onStatus(HttpStatus::is5xxServerError, clientResponse ->Mono.error(new GenericException("E9999",clientResponse.bodyToMono(String.class).block())))
				.bodyToMono(RandomObject.class)
				.map(randomObject -> randomObject.getRandomNumber())
				.switchIfEmpty(Mono.error(new RandomNotFoundException()))
		);
	}
	
}
