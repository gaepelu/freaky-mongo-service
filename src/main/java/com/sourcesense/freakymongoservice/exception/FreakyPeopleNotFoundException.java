package com.sourcesense.freakymongoservice.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility=Visibility.NONE, getterVisibility=Visibility.NONE) 
public class FreakyPeopleNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String error;
	private String message;
	public FreakyPeopleNotFoundException(String id){
		this.id = id;
		this.error = "E1001";
		this.message = "Freaky perople with id "+this.id+" not found";
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

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	
	
}
