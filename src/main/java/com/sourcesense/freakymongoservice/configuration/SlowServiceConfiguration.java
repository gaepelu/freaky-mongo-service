package com.sourcesense.freakymongoservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix="freaky-slow-service")
public class SlowServiceConfiguration {
	private String randomUrl;
	private String randomSlowUrl;
	private String randomsUrl;
}
