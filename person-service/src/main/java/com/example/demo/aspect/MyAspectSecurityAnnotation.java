package com.example.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.aspect.annotation.ToMyCustomSecurityService;
import com.example.demo.aspect.utilities.SecuritySender;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class MyAspectSecurityAnnotation implements SecuritySender{
	
	@Autowired
	WebClient webClient;


	@Before("@annotation(toMyCustomSecurityService)")
	public void goToSecured(ToMyCustomSecurityService toMyCustomSecurityService) throws Exception {
	    String securityEndpointService = toMyCustomSecurityService.securityEndpointService();

	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    String token = request.getHeader("Authorization");
	    
	    Boolean secured=verifySecurityAccess(token, securityEndpointService,webClient);
//	    if(!secured)
//	    {
//	    	throw new Exception("Not secured!");
//	    }
	    System.err.println("Secured!");

	}
	

	
	
}

	

	

