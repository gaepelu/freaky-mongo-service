package com.sourcesense.freakymongoservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.sourcesense.freakymongoservice.exception.BeforeSaveValidationException;
import com.sourcesense.freakymongoservice.exception.GenericException;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BeforeSaveValidationException.class)
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Mono<BeforeSaveValidationException> beforeSaveValidationExceptionHandler(BeforeSaveValidationException ex) {
		return Mono.just(ex);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Mono<GenericException> handleException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		
		String message = fieldErrors.stream().map(ve -> ve.getDefaultMessage()).collect(Collectors.joining(", "));
	 
		return Mono.just(new GenericException("E2003", message));
	}
	
	@ExceptionHandler(WebExchangeBindException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Mono<GenericException> handleGenericException(WebExchangeBindException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		
		String message = fieldErrors.stream().map(ve -> ve.getDefaultMessage()).collect(Collectors.joining(", "));
	 
		return Mono.just(new GenericException("E2004", message));
	}
	
	@ExceptionHandler(GenericException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Mono<GenericException> GenericExceptionHandler(GenericException ex) {
		return Mono.just(ex);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Mono<GenericException> generic(Exception ex) {
		return Mono.just(new GenericException("E9999", ex.getMessage()));
	}
}
