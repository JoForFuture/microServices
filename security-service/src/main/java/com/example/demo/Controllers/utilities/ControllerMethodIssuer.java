package com.example.demo.Controllers.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;
import com.example.demo.model.ViewManager;

import jakarta.servlet.http.HttpSession;


public interface ControllerMethodIssuer {
	
//	private final ReactorLoadBalancerExchangeFilterFunction lbFunction;
	final String personServiceAddToPeopleGroup="http://person-service/managePeopleGroup/private/addToPeopleGroup";
    final String personServiceSearchPerson="http://person-service/managePeopleGroup/searchPerson";
	final String personServiceUpdateMemberOfPeopleGroup="http://person-service/managePeopleGroup/private/updateMemberOfPeopleGroup/";
//	PersonRequest personRequestTransient=null;
	

	
	public default Optional<PersonResponse> findAndGetPersonOptional(PersonRequest personRequest, HttpSession session,ReactorLoadBalancerExchangeFilterFunction lbFunction)
	{
		System.err.println("metodo: findAndGetPersonOptional");
		HttpHeaders httpHeaders=MyHttpTrasporter.authJsonCarrier(session);

		return Optional.ofNullable( WebClient.builder()
				.filter(lbFunction) //SACRO GRAL DELLE RICHIESTE CON WEBCLIENT!
				.build()
				.post()
				.uri(personServiceSearchPerson)
				.headers(headers->headers.addAll(httpHeaders))
				.bodyValue(personRequest)
				.retrieve() 
				.bodyToMono(PersonResponse.class)
				.block());
		
	}
//	#2
	public default void personGettedViewIssuer(PersonResponse personInMemory,HttpSession session,Model model)
	{
//			String queryParamInMem = "?inMemory=true";
		System.err.println("metodo: returnPersonToView");

			String isInMemoryStringResponse="Already in memory";
        	Map<String,Object> attributesMap=new HashMap<String,Object>();
        	attributesMap.put("response",isInMemoryStringResponse);
        	attributesMap.put("person", personInMemory);
			
			ViewManager
					.builder()
					.attributesMap(attributesMap)
					.getPersonDetailsPage_isVisible(true)
					.build()
						.updateView(session, model);
			
			 
							

	}
//	#3
	public default PersonResponse createNewPerson(HttpHeaders httpHeaders,PersonRequest personRequest,ReactorLoadBalancerExchangeFilterFunction lbFunction)
	{
		System.err.println("metodo: createdNewPerson ");

//		String queryParamInMem = "?inMemory=false";
		return
					 WebClient.builder()
					.filter(lbFunction) 
					.build()
					.post()
					.uri(personServiceAddToPeopleGroup)
					.headers(headers->headers.addAll(httpHeaders))
					.bodyValue(personRequest)
					.retrieve() 
					.bodyToMono(PersonResponse.class)
					.block();
				

}
//	#4
	public default void personNotFoundViewIssuer(HttpSession session,Model model){
		String stringResponse="not found";
    	Map<String,Object> attributesMap=new HashMap<String,Object>();
    	attributesMap.put("response",stringResponse);
    	ViewManager
    					.builder()
    						.getErrorPage_isVisible(true)
    						.attributesMap(attributesMap)
    					.build()
    						.updateView(session, model);
	}
	
//	#5
	public default PersonResponse updatePerson(HttpHeaders headers,PersonRequest personRequest,ReactorLoadBalancerExchangeFilterFunction lbFunction,Long id)
	{
		
        String queryParamMethod="?_method=PUT";

		return 	WebClient.builder()
				.filter(lbFunction)
				.build()
				.put()
				.uri(personServiceUpdateMemberOfPeopleGroup+id+queryParamMethod)
				.headers(httpHeaders->httpHeaders.addAll(headers))
				.bodyValue(personRequest)
				.retrieve()
				.bodyToMono(PersonResponse.class)
				.block();
	}
}
