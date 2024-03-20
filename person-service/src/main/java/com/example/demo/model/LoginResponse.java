package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
	
	
	private final String accessToken;
	
	private final String successUrl;//not used
	


}
