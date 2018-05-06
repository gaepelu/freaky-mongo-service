package com.sourcesense.freakymongoservice.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sourcesense.freakymongoservice.datatype.FreakyPeople;
import com.sourcesense.freakymongoservice.exception.FreakyPeopleNotFoundException;
import com.sourcesense.freakymongoservice.service.FreakyPeopleService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FreakyPeopleController {
	
	@Autowired
	private FreakyPeopleService freakyPeopleService;
	
	@PostMapping("/freaky-people")
	public Mono<FreakyPeople> saveFreakyPeople(@RequestBody @Valid FreakyPeople freakyPeople){
		return freakyPeopleService.saveFreakyPeople(freakyPeople);
	}
	
	@GetMapping("/freaky-people")
	public Flux<FreakyPeople> getFreakyPeople(final @RequestParam(name = "page") int page,final @RequestParam(name = "size") int size){
		return freakyPeopleService.getFreakyPeople(PageRequest.of(page, size));
	}
	
	@GetMapping("/freaky-people/{id}")
	public Mono<FreakyPeople> getFreakyPeopleById(@PathVariable(required=true,name="id") String id){
		return freakyPeopleService.getFreakyPeopleById(id)
				.switchIfEmpty(Mono.error(new FreakyPeopleNotFoundException(id)));
	}
	
	@DeleteMapping("/freaky-people/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public Mono<Void> deleteFreakyPeople(@PathVariable("id") String id){
		return freakyPeopleService.deleteById(id);
	}
	
	@ExceptionHandler(FreakyPeopleNotFoundException.class)
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	@ResponseBody
	public Mono<FreakyPeopleNotFoundException> freakyPeopleNotFoundExceptionHandler(FreakyPeopleNotFoundException ex) {
		return Mono.just(ex);
	}
	
}
