package com.sourcesense.freakymongoservice.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.NONE, getterVisibility=Visibility.NONE) 
public class GenericException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String error;
	private String message;
	public GenericException(String error, String message) {
		this.error = error;
		this.message = message;
	}
	
	@JsonProperty
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	@JsonProperty
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
