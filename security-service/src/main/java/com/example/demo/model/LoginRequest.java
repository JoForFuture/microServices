package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter//aggiunto
@Builder
@AllArgsConstructor
public class LoginRequest {
	
	
	public LoginRequest() { //aggiunto
	}
	private String email;
	private String password;


}


