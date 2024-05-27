package com.example.demo.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.configuration.JwtProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
	
	@Autowired
	private JwtProperties jwtProperties;
	
	public String issue(long userId,String email,List<String> roles) {
		return JWT.create()
				.withSubject(String.valueOf(userId))
				.withExpiresAt(Instant.now().plus(600000, ChronoUnit.MILLIS))
				.withClaim("e", email)
				.withClaim("a", roles)
				.sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
		
	}

}
