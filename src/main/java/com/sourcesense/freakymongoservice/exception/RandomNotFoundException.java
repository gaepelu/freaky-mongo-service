package com.sourcesense.freakymongoservice.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.NONE, getterVisibility=Visibility.NONE) 
public class RandomNotFoundException extends GenericException{

	private static final long serialVersionUID = 1L;

	public RandomNotFoundException(String error, String message) {
		super(error, message);
		// TODO Auto-generated constructor stub
	}

}
