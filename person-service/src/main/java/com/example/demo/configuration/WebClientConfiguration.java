package com.example.demo.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.model.Cane;

@Configuration
public class WebClientConfiguration {
	

	@Bean
    @LoadBalanced
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
	
	@Bean
	
	public Cane caneCinque()
	{
		return new Cane(5);
	}
	
	@Bean
	@Primary
	public Cane caneSei()
	{
		return new Cane(6);
	}


}
