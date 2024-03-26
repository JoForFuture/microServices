package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.Services.PersonService;
import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;
import com.example.demo.model.ViewManager;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/managePeopleGroup")
public class PersonController {

	@Autowired
	PersonService personService;
	
	@Autowired 
	WebClient webClient;

//	@Autowired
//	PersonDTO personDTO;

	public PersonController() { 
	};

	// aggiungi persona--- C
	@PostMapping("/private/addToPeopleGroup")
	public String addToPeople(@ModelAttribute("person") PersonRequest personRequest, HttpSession session,Model model) {
	     
		String authorization=(String) session.getAttribute("Authorization");

		 HttpHeaders headers = new HttpHeaders();
	     headers.setContentType(MediaType.APPLICATION_JSON);
	     headers.set("Authorization", authorization);
	     
	     //controllo se esiste  
	     PersonResponse personInMemory=webClient.post()
					.uri("http://localhost:8082/managePeopleGroup/searchPerson")
	                .headers(httpHeaders -> httpHeaders.addAll(headers))
	                .bodyValue(personRequest)
	                .retrieve()
	                .bodyToMono(PersonResponse.class)
	                .block(); 
		 if(personInMemory!=null) {
			 String queryParamInMem="?inMemory=true";

			 return "redirect:http://localhost:8081/managePeopleGroup/getMemberOfPeopleGroup/"+personInMemory.getId()+queryParamInMem;
			 };//se !=null esci non aggiungo perchè c'è
		
			
			 String queryParamInMem="?inMemory=false";
		 Long id=webClient.post()
				.uri("http://localhost:8082/managePeopleGroup/private/addToPeopleGroup")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(personRequest)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
		 

		 return "redirect:http://localhost:8081/managePeopleGroup/getMemberOfPeopleGroup/"+id+queryParamInMem;
 
	
	}
	
	@PostMapping("/searchPerson")
	public String searchPerson(@ModelAttribute("person") PersonRequest personRequest, HttpSession session,Model model) {
		
		String authorization=(String) session.getAttribute("Authorization");
		
		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("Authorization",authorization);	
	        
	        Optional<PersonResponse> personInMemory=Optional.ofNullable(webClient.post()
				.uri("http://localhost:8082/managePeopleGroup/searchPerson")
             .headers(httpHeaders -> httpHeaders.addAll(headers))
             .bodyValue(personRequest)
             .retrieve()
             .bodyToMono(PersonResponse.class)
             .block());
	        if(personInMemory.isEmpty()) {
				 String queryParamInMem="?inMemory=false";
	        	return "redirect:http://localhost:8081/managePeopleGroup/getMemberOfPeopleGroup/"+-1+queryParamInMem;
	        }
			 String queryParamInMem="?inMemory=true";

	        	
		
		 return "redirect:http://localhost:8081/managePeopleGroup/getMemberOfPeopleGroup/"+personInMemory.get().getId()+queryParamInMem;
		
}

	// recupera persona da ID---R
	@GetMapping("/getMemberOfPeopleGroup/{id}")
	public String getMemberOfPeopleGroup(@PathVariable("id") String id, @RequestParam("inMemory") boolean inMemory,
			HttpSession session, Model model) {
		 
		String isInMemoryStringResponse="";
			if(inMemory){isInMemoryStringResponse="Already in memory";
			}else {isInMemoryStringResponse="Added new person";};
		 	HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        
	        
	        PersonResponse personReponse= webClient.get()
	          				.uri("http://localhost:8082/managePeopleGroup/getMemberOfPeopleGroup/"+id)
	        				.headers(httpHeaders -> httpHeaders.addAll(headers))
	        				.retrieve()
	        				.bodyToMono(PersonResponse.class)
	        				.block();
	        
	        String stringResponse;
	        if(personReponse==null) {
	        	stringResponse="not found";
	        	Map<String,Object> attributesMap=new HashMap<String,Object>();
	        	attributesMap.put("response",stringResponse);
	        	ViewManager
	        					.builder()
	        						.getErrorPage_isVisible(true)
	        						.attributesMap(attributesMap)
	        					.build()
	        						.updateView(session, model);
	        	return "Index";
	        					
	        }
        	stringResponse=isInMemoryStringResponse;
        	Map<String,Object> attributesMap=new HashMap<String,Object>();
        	attributesMap.put("response",stringResponse);
        	attributesMap.put("person", personReponse);
        	
			ViewManager
							.builder()
							.getPersonDetailsPage_isVisible(true)
							.attributesMap(attributesMap)
							.build()
							.updateView(session, model);
			

			return "Index";
	}

	// aggiorna persona da id--- U
	@PutMapping("/private/updateMemberOfPeopleGroup/{id}") //
	public String updateMemberOfPeopleGroup(@PathVariable("id") Long id,@ModelAttribute("person") PersonRequest personRequest) {
		
	 	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        String queryParamMethod="?_method=PUT";
        String queryParamInMem="?inMemory=true";
        
			PersonResponse response=webClient.post() 
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
	public String deleteMemberOfPeopleGroup(@PathVariable("id") Long id ) {
		
		String queryParamMethod="?_method=DELETE";
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
		
		Long deletedIds=webClient.delete()
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
