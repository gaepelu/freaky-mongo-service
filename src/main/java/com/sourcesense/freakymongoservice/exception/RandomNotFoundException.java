package com.sourcesense.freakymongoservice.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.NONE, getterVisibility=Visibility.NONE) 
public class RandomNotFoundException extends GenericException{

	private static final long serialVersionUID = 1L;

	public RandomNotFoundException() {
		super("E1223", "no random numbert found");
		// TODO Auto-generated constructor stub
	}

}
