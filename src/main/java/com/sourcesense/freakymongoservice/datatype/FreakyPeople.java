package com.sourcesense.freakymongoservice.datatype;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FreakyPeople {
	@Id
	private String id;
	@NotEmpty(message="name empty")
	private String name;
	@NotEmpty(message="surname empty")
	private String surname;
	@NotEmpty(message="email empty")
	@Indexed(unique=true,name="freaky-people-email")
	private String email;
	@Transient
	private Double favoriteNumber;
	@Transient
	private String favoriteUUID;
	@CreatedDate
	private Instant createdDate;
}