package com.sourcesense.freakymongoservice.repository.reactive.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com.sourcesense.freakymongoservice.datatype.FreakyPeople;
import com.sourcesense.freakymongoservice.repository.reactive.FreakyPeopleReactiveMongoTemplate;

import reactor.core.publisher.Flux;

@Repository
public class FreakyPeopleReactiveRepositoryImpl implements FreakyPeopleReactiveMongoTemplate{
	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;

	@Override
	public Flux<String> test() {
		Aggregation aggregation = newAggregation(project("name"));
		Flux<String> val = reactiveMongoTemplate.aggregate(aggregation,"freakyPeople",FreakyPeople.class ).map(el -> el.getName());
		return val;
	}
	
}
