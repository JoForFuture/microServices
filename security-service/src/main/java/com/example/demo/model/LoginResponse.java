package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
	
	
	private final String accessToken;
	
	private final String nextUrl;
	
	
	
	

	


}
