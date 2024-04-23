package com.example.demo.tosecurityservice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class MyAspectSecurityAnnotation implements SecuritySender{
	
	@Autowired
	WebClient.Builder webClientBuilder;


	@Before("@annotation(toMyCustomSecurityService)")
	public void goToSecured(ToMyCustomSecurityService toMyCustomSecurityService) throws Exception {
		
	    String securityEndpointService = toMyCustomSecurityService.securityEndpointService();

	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    String token = request.getHeader("Authorization");
	 
	    
	    Boolean secured=verifySecurityAccess(token, securityEndpointService,webClientBuilder);
	    if(!secured)
	    {
	    	throw new Exception("Not secured!");
	    }
	    System.err.println("Secured!");

	}
	
	

	
	
}

	

	

