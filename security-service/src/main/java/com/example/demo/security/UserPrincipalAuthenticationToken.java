package com.example.demo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

	private final UserPrincipal principal;

	public UserPrincipalAuthenticationToken(UserPrincipal principal) {
		super(principal.getAuthorities());
		this.principal=principal;
		setAuthenticated(true);//aggiunto ma da verificare****
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
//		List credential=new ArrayList<String>();
//		credential.add(String.valueOf(principal.getUserId()));
//		credential.add(principal.getPassword());
		
		return null; 
//				
	}

	@Override
	public UserPrincipal getPrincipal() {
		// TODO Auto-generated method stub
		
		return principal;
	}

}
