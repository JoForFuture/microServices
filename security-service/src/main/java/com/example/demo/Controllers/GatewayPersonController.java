package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.Entities.Person;
import com.example.demo.Services.PersonService;
import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;
import com.example.demo.model.ViewManager;
import com.example.demo.security.UserPrincipalAuthenticationToken;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/view/person")
public class GatewayPersonController {

	@Autowired
	PersonService personService;
	
	
	 
	private final WebClient.Builder webClientBuilder;
	
	@Autowired
	private final ReactorLoadBalancerExchangeFilterFunction lbFunction;
	
	
	private final String propagatedException="propagatedException";

//	@Autowired
//	PersonDTO personDTO;  

	@GetMapping("/getAll")
	public String getAll( HttpSession session,Model model,PersonRequest personRequest,@AuthenticationPrincipal UserPrincipalAuthenticationToken userPrincipalAuthenticationToken) throws NotFoundException {
					

		String authorization=(String) session.getAttribute("Authorization");
		
		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON); 
	        headers.set("Authorization",authorization);	
	      

	        try {
	        Person[] personArray=
	        		WebClient.builder()
					 .filter(lbFunction) 
					 .build()
					 .get()
					 .uri(uribuilder->uribuilder
							 				.scheme("http")
							 				.host("person-service")
							 				.path("/getAll")
							 				.build())
					 .headers(httpHeaders -> httpHeaders.addAll(headers))
					 .retrieve()
					 .bodyToMono(Person[].class)
					 .log()
					 .block();
	        
	        String  stringResponse="found";
	        Map<String,Object> attributesMap=new HashMap<String,Object>();
       	attributesMap.put("response",stringResponse);
       	attributesMap.put("personArray",personArray);
       	attributesMap.put("person", personRequest);
       	
       	
	    	ViewManager
			.builder()
				.formSearchPerson_isVisible(true)
				.getPersonaArray_isVisible(true)
				.attributesMap(attributesMap)
			.build()
				.updateView(session, model);
	    	
	    	
	        }catch(WebClientResponseException e)
	        {
	        	 	session.setAttribute(propagatedException, e);
		        	String errorMessage="Not found";
		        	return "redirect:/api/view/person/errorPage"+"?errorMessage="+errorMessage;
			    	
	        }
       	return "Index";
		
//				return new ResponseEntity<PersonResponse> (HttpStatus.NOT_FOUND);
			

	}
	 
	@GetMapping("/getByNameAndSurname")
	public String searchPerson(@RequestParam("surname") String surname,@RequestParam("name") String name,HttpSession session,Model model,HttpServletResponse httpServletResponse) {
		
		String authorization=(String) session.getAttribute("Authorization");
		
		System.err.println(session.getId());

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
							 				.path("/getByNameAndSurname")
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
	        	 	session.setAttribute(propagatedException, e);
		        	String errorMessage="Not found";
		        	return "redirect:/api/view/person/errorPage"+"?errorMessage="+errorMessage;
			    	
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
							 				.path("/getById")
							 				.queryParam("id",id)
							 				.build())
					 .headers(httpHeaders -> httpHeaders.addAll(headers))
					 .retrieve()
					 .toEntity(PersonResponse.class)
					 .log()
					 .block();
	        
	        
	        String  stringResponse="Found";
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
		   	    	
		   	 	session.setAttribute(propagatedException, e);
	        	String errorMessage="Unable to retrieve person information";
	        	return "redirect:/api/view/person/errorPage"+"?errorMessage="+errorMessage;
			    	
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
								.path("/private/add")
								.build())
						.bodyValue(personRequest)
						.headers(httpHeaders -> httpHeaders.addAll(headers))
						.retrieve()
						.bodyToMono(Long.class)
						.log()
						.block();
				 
				 return "redirect:/api/view/person/getById"+"?id="+idPerson;

				

			} catch (WebClientResponseException e) {
				String errorMessage = "Insert failed";
				session.setAttribute(propagatedException, e);
	        	return "redirect:/api/view/person/errorPage"+"?errorMessage="+errorMessage;
				
				

			}
	     
	  
	}
	
	
	
	// aggiorna persona da id--- U
	@PutMapping("/private/update") //
	public String updateMemberOfPeopleGroup(@RequestParam("id") Long id,@ModelAttribute("person") PersonRequest personRequest,HttpSession session) {
	
        
        String authorization=(String)session.getAttribute("Authorization");
		
	 	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",authorization);	
        
//        String queryParamMethod="?_method=PUT";
        
		try {
			 ResponseEntity<Long> p= WebClient.builder()
					.filter(lbFunction)
					.build()
					.put()
					.uri(uribuilder -> uribuilder
							.scheme("http")
							.host("person-service")
							.path("/private/update")
							.queryParam("id", id)
							.build())
					.bodyValue(personRequest)
					.headers(httpHeaders -> httpHeaders.addAll(headers))
					.retrieve()
					.toEntity(Long.class)
					.log()
					.block();
			 
			 return "redirect:/api/view/person/getById"+"?id="+id;

			

		} catch (WebClientResponseException e) {
			String errorMessage = "Update failed";
			session.setAttribute(propagatedException, e);
       	return "redirect:/api/view/person/errorPage"+"?errorMessage="+errorMessage;
		}
        

	};
	
	@DeleteMapping("/private/delete") //
	public String deleteMemberOfPeopleGroup(@RequestParam("id") Long id ,HttpSession session) {
		
		  	String authorization=(String)session.getAttribute("Authorization");
			
		 	HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("Authorization",authorization);	
        
		try {
		ResponseEntity<Long> deletedIds=WebClient.builder()
						.filter(lbFunction)
						.build()
						.delete()
						.uri(uriBuilder->
							uriBuilder.scheme("http")
									  .host("person-service")
									  .path("/private/delete")
									  .queryParam("id", id)
									  .build()
						)
						.headers(httpHeaders->httpHeaders.addAll(headers))
						.retrieve()
						.toEntity(Long.class)
						.block();
		
		System.err.println("person deleted");

//		return "redirect:/person/getById"+"?id="+id;
		return "redirect:/gestionale/in/view";
		
		} catch (WebClientResponseException e) {
			String errorMessage = "Operation delete failed";
			session.setAttribute(propagatedException, e);
			
			return "redirect:/api/view/person/errorPage"+"?errorMessage="+errorMessage;
		}
    

	};
	
	
	@GetMapping("/errorPage")
	public String getMemberOfPeopleGroupErrorPage(@RequestParam("errorMessage") String errorMessage,Model model, HttpSession session,
			HttpServletResponse httpServletResponse) {

		
    	Map<String,Object> attributesMap=new HashMap<String,Object>();
       	attributesMap.put("errorMessage",errorMessage);
       	
       	WebClientResponseException exception=(WebClientResponseException)session.getAttribute(propagatedException);
       	attributesMap.put(propagatedException, exception);
       	
	    	ViewManager
			.builder()
				.getErrorPage_isVisible(true)
				.attributesMap(attributesMap)
			.build()
				.updateView(session, model);
	    	httpServletResponse.setStatus(exception.getStatusCode().value());
		
		
	
						
		return "Index";
	} 

}
