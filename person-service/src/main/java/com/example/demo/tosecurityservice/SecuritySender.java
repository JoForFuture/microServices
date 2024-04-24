
package com.example.demo.tosecurityservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/*
 * prova javadoc
 */
public interface SecuritySender {
	
	
	
	default boolean verifySecurityAccess(String authorization,String sendToSecurityService, WebClient.Builder webClientBuilde,ReactorLoadBalancerExchangeFilterFunction lbFunction)
	{
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", authorization);
//		lo mando indietro al servizio di security
		
		return WebClient.builder()
					.filter(lbFunction)
					.build()
					.post()
					.uri(sendToSecurityService)
					.headers(httpHeaders->httpHeaders.addAll(headers))
					.retrieve()
					.bodyToMono(Boolean.class)
					.block();
	

	}
	@Deprecated
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
