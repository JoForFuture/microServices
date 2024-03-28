package com.example.demo.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
	
	@Bean
	@Primary
    @LoadBalanced
	public WebClient webClient() {
		return WebClient.builder().build();
//		return WebClient.create();

	}
	
	@Bean
	@Primary
    @LoadBalanced
    public RestTemplate restTemplate()
    {
		return new RestTemplate();
    }
	
	
//		@Bean
//	    @Primary
//	    @LoadBalanced
//	    public WebClient webClient() {
//	        HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
//	        return WebClient.builder()
//	                .clientConnector(new ReactorClientHttpConnector(httpClient))
//	                .build();
//	    }

}
