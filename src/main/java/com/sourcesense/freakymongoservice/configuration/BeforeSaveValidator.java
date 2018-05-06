package com.sourcesense.freakymongoservice.configuration;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import com.sourcesense.freakymongoservice.exception.BeforeSaveValidationException;


@Component
public class BeforeSaveValidator extends AbstractMongoEventListener<Object> {

  @Autowired
  private Validator validator;

  @Override
  public void onBeforeSave(BeforeSaveEvent<Object> event) {
	  
	  Object source = event.getSource();
      Set<ConstraintViolation<Object>> violations = validator.validate(source);
      if (violations.size() > 0) {
    	  String message = violations.stream().map(v->v.getMessage()).collect(Collectors.joining(", "));
    	  throw new BeforeSaveValidationException("E2000",message);
      }
      
  }
}