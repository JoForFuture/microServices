package com.example.demo.aspect.utilities;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


public interface SecuritySender {
	
	
	
	default boolean verifySecurityAccess(String authorization,String sendToSecurityService, WebClient webClient)
	{
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", authorization);
//		lo mando indietro al servizio di security
		
		return webClient.post()
					.uri(sendToSecurityService)
					.headers(httpHeaders->httpHeaders.addAll(headers))
					.retrieve()
					.bodyToMono(Boolean.class)
					.block();
	

	}
	
	default boolean verifySecurityAccess(String authorization,String sendToSecurityService, RestTemplate restTemplate)
	{
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", authorization);
//		lo mando indietro al servizio di security

		
		return 
				restTemplate.postForEntity(sendToSecurityService, headers, Boolean.class).getBody();
		
	

	}

}
