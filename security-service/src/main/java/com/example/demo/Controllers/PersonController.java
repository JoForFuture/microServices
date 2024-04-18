package com.example.demo.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.Services.PersonService;
import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;
import com.example.demo.model.ViewManager;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService personService;
	
	
	
	@Autowired
	RestTemplate restTemplate; 
	
	 
	private final WebClient.Builder webClientBuilder;
	
	@Autowired
	private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

//	@Autowired
//	PersonDTO personDTO;  

	 
	@GetMapping("/getByNameAndSurname")
	public String searchPerson(@RequestParam("surname") String surname,@RequestParam("name") String name,HttpSession session,Model model,HttpServletResponse httpServletResponse) {
		
		String authorization=(String) session.getAttribute("Authorization");
		
		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON); 
	        headers.set("Authorization",authorization);	

	        try {
	        PersonResponse personInMemory=
	        		WebClient.builder()
					 .filter(lbFunction) 
					 .build()
					 .get()
					 .uri(uribuilder->uribuilder
							 				.scheme("http")
							 				.host("person-service")
							 				.path("/person/getByNameAndSurname")
							 				.queryParam("surname",surname)
							 				.queryParam("name",name)
							 				.build())
					 .headers(httpHeaders -> httpHeaders.addAll(headers))
					 .retrieve()
					 .bodyToMono(PersonResponse.class)
					 .log()
					 .block();
	        
	        String  stringResponse="found";
	        Map<String,Object> attributesMap=new HashMap<String,Object>();
        	attributesMap.put("response",stringResponse);
        	attributesMap.put("person",personInMemory);
	    	ViewManager
			.builder()
				.getPersonDetailsPage_isVisible(true)
				.attributesMap(attributesMap)
			.build()
				.updateView(session, model);
	    	
	    	
	        }catch(WebClientResponseException e)
	        {
	        	 
		        	String errorMessage="Person not found";
		        	Map<String,Object> attributesMap=new HashMap<String,Object>();
		           	attributesMap.put("errorMessage",errorMessage);
		           	
		   	    	ViewManager
		   			.builder()
		   				.getErrorPage_isVisible(true)
		   				.attributesMap(attributesMap)
		   			.build()
		   				.updateView(session, model);
		   	    	httpServletResponse.setStatus(e.getStatusCode().value());
			    	
	        }
        	return "Index";

}

	@GetMapping("/getById")
	public String getById(@RequestParam("id") Long id, HttpSession session,Model model,HttpServletResponse httpServletResponse)
	{
		String authorization=(String) session.getAttribute("Authorization");

		 HttpHeaders headers = new HttpHeaders();
	     headers.setContentType(MediaType.APPLICATION_JSON);
	     headers.set("Authorization", authorization);
		try {
	        ResponseEntity<PersonResponse> personInMemory=
	        		WebClient.builder()
					 .filter(lbFunction) 
					 .build()
					 .get()
					 .uri(uribuilder->uribuilder
							 				.scheme("http")
							 				.host("person-service")
							 				.path("/person/getById")
							 				.queryParam("id",id)
							 				.build())
					 .headers(httpHeaders -> httpHeaders.addAll(headers))
					 .retrieve()
					 .toEntity(PersonResponse.class)
					 .log()
					 .block();
	        
	        
	        String  stringResponse="Insert complete";
	        Map<String,Object> attributesMap=new HashMap<String,Object>();
     	attributesMap.put("response",stringResponse);
     	attributesMap.put("person",personInMemory.getBody());
	    	ViewManager
			.builder()
				.getPersonDetailsPage_isVisible(true)
				.attributesMap(attributesMap)
			.build()
				.updateView(session, model);
	    	
	    	httpServletResponse.setStatus(personInMemory.getStatusCode().value());
	   
	    	
	        }catch(WebClientResponseException e)
	        {
	        	 
		        	String errorMessage="Unable to retrieve person information";
		        	Map<String,Object> attributesMap=new HashMap<String,Object>();
		           	attributesMap.put("errorMessage",errorMessage);
		           	
		   	    	ViewManager
		   			.builder()
		   				.getErrorPage_isVisible(true)
		   				.attributesMap(attributesMap)
		   			.build()
		   				.updateView(session, model);
		   	    	httpServletResponse.setStatus(e.getStatusCode().value());
			    	
	        }
		return "Index";
	}
	
	
	
	// aggiungi persona--- C
	@PostMapping("/private/add")
	public String addToPeople(@ModelAttribute("person") PersonRequest personRequest, HttpSession session,Model model,HttpServletResponse httpServletResponse) {
	     
		String authorization=(String) session.getAttribute("Authorization");

		 HttpHeaders headers = new HttpHeaders();
	     headers.setContentType(MediaType.APPLICATION_JSON);
	     headers.set("Authorization", authorization);
	     
	     Long idPerson=-1l;
			try {
				 idPerson= WebClient.builder()
						.filter(lbFunction)
						.build()
						.post()
						.uri(uribuilder -> uribuilder
								.scheme("http")
								.host("person-service")
								.path("person/private/add")
								.build())
						.bodyValue(personRequest)
						.headers(httpHeaders -> httpHeaders.addAll(headers))
						.retrieve()
						.bodyToMono(Long.class)
						.log()
						.block();
				 
				 return "redirect:http://localhost:8081/person/getById"+"?id="+idPerson;
				 
				 //DA TENERE DA CONTO PER MODIFICARE SOPRA

//
//					    public Mono<String> getById(String idPerson) {
//					        return webClient.get()
//					                .uri("/person/getById?id={id}", idPerson)
//					                .retrieve()
//					                .bodyToMono(String.class)
//					                .map(response -> "redirect:" + response); // Assume che la risposta contenga l'URL a cui fare redirect
//					    }
//					}
	  

			} catch (WebClientResponseException e) {
				String errorMessage = "Insert failed";
				Map<String, Object> attributesMap = new HashMap<String, Object>();
				attributesMap.put("errorMessage", errorMessage);

				ViewManager.builder().getErrorPage_isVisible(true).attributesMap(attributesMap).build()
						.updateView(session, model);
				httpServletResponse.setStatus(e.getStatusCode().value());

			}
	     
	     return "Index";
	  
	}
	
	
	
	// aggiorna persona da id--- U
	@PutMapping("/private/updateMemberOfPeopleGroup/{id}") //
	public String updateMemberOfPeopleGroup(@PathVariable("id") Long id,@ModelAttribute("person") PersonRequest personRequest,HttpSession session) {
	
        
        String authorization=(String)session.getAttribute("Authorization");
		
	 	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",authorization);	
        
        String queryParamMethod="?_method=PUT";
        String queryParamInMem="?inMemory=true";
        
			PersonResponse response=WebClient.builder().build().post() 
						.uri("http://localhost:8082/managePeopleGroup/private/updateMemberOfPeopleGroup/"+id+queryParamMethod)
//						.uri(uriBuilder -> uriBuilder
//							    .scheme("http") 
//							    .host("localhost") 
//							    .port(8082)
//							    .path("/managePeopleGroup/private/updateMemberOfPeopleGroup/{id}") 
//							    .queryParam("_method", "PUT") 
//							    .build(id))
						.headers(httpHeaders->httpHeaders.addAll(headers))
						.bodyValue(personRequest)
						.retrieve()
			   			.bodyToMono(PersonResponse.class)
						.block();


		return "redirect:http://localhost:8081/managePeopleGroup/getMemberOfPeopleGroup/" + id+queryParamInMem;

	};
	
	@DeleteMapping("/private/deleteMemberOfPeopleGroup/{id}") //
	public String deleteMemberOfPeopleGroup(@PathVariable("id") Long id ,HttpSession session) {
		
		String queryParamMethod="?_method=DELETE";
		
		  String authorization=(String)session.getAttribute("Authorization");
			
		 	HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("Authorization",authorization);	
        
		
		Long deletedIds=WebClient.builder().build().delete()
						.uri("http://localhost:8082/managePeopleGroup/private/deleteMemberOfPeopleGroup/"+id+queryParamMethod)
						.headers(httpHeaders->httpHeaders.addAll(headers))
						.retrieve()
						.bodyToMono(Long.class)
						.block();
		
		System.err.println("person deleted");

		return "redirect:/searchPerson/view";

	};
	
	

//*****************************************************GESTIONE ERRORI
	// errore sull'inserimento persona
	@GetMapping("/errorPage")
	public String getMemberOfPeopleGroupErrorPage(Model model, HttpSession httpSession,
			@ModelAttribute("errorMessage") String errorMessageResponse) {

        Map<String,Object> attributesMap=new HashMap<>();
        attributesMap.put("errorMessage",errorMessageResponse);

		ViewManager
						.builder()
						.getErrorPage_isVisible(true)
						.attributesMap(attributesMap)
						.build()
							.updateView(httpSession, model);
						
		return "Index";
	} 

}
