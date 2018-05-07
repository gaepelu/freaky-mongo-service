package com.sourcesense.freakymongoservice.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.NONE, getterVisibility=Visibility.NONE) 
public class FreakyPeopleNotFoundException extends GenericException{
	private static final long serialVersionUID = 1L;
	
	public FreakyPeopleNotFoundException(String id) {
		super("E1001", "Freaky perople with id "+id+" not found");
	}
	
}
