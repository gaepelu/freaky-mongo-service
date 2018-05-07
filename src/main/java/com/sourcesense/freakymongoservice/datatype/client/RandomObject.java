package com.sourcesense.freakymongoservice.datatype.client;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class RandomObject {
	private Double randomNumber;
	private String randomString;
}
