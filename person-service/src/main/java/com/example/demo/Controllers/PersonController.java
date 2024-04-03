package com.example.demo.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.Entities.Person;
import com.example.demo.Services.PersonService;
import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;
import com.example.demo.model.ViewManager;
import com.giogio.SecuredByCustomSecServ.aspect.annotation.ToMyCustomSecurityService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@RestController//ricorda hai cambiato quii!!
@RequestMapping("/managePeopleGroup")
public class PersonController {

	@Autowired
	PersonService personService;
	
	@Autowired
	WebClient webClient;
	
	private static final String securityServiceEndpoint="http://localhost:8081/securityControl/accessPoint";
//
//	@Autowired
//	PersonDTO personDTO;

	public PersonController() { 
	};

	// MediaType.APPLICATION_FORM_URLENCODED_VALUE
	// aggiungi persona--- C
	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@PostMapping(path = "/private/addToPeopleGroup", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Long addToPeople(@RequestBody PersonRequest personRequest, HttpSession session,Model model) {
//		Boolean isAuthenticated=verifySecurityAccess(authorization,securityServiceEndpoint);

		Person personFromRequest = fromPersonRequestToPerson(personRequest);
			try {				  return personService.save(personFromRequest).getId();}
			catch(Exception e)
			{e.printStackTrace(); return -1l;}


	}
	

	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@PostMapping(path = "/searchPerson", consumes = MediaType.APPLICATION_JSON_VALUE)
	public PersonResponse searchPerson(
												@RequestBody PersonRequest personRequest, HttpSession session,Model model) {
					
//		@RequestHeader("Authorization") String authorization,

//			Boolean isAuthenticated=verifySecurityAccess(authorization,securityServiceEndpoint);
//
//
//			if(isAuthenticated==false) {
//				System.err.println("NOT AUTENTICATED");
//				return null;
//			}
//			System.err.println("AUTENTICATED"+isAuthenticated);
//			controlla che è uguale a quello interno,
//			mi rimanda l'ok
			 Person personFromRequest=fromPersonRequestToPerson(personRequest);

			 Optional<Person> personInMemory = personService.nameAndSurnameNotEmpty(personFromRequest)
					.findByNameAndSurnameIgnoreCase(personFromRequest);
			if (personInMemory.isPresent()) {
				 PersonResponse pr=fromPersonToPersonResponse(personInMemory.get());
				 return pr;}
			else {
				return null;}

	}


	// recupera persona da ID---R
	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@GetMapping("/getMemberOfPeopleGroup/{id}")
	public PersonResponse getMemberOfPeopleGroup(@PathVariable String id, String authorization, 
			HttpSession session, Model model) throws EntityNotFoundException{
//		Boolean isAuthenticated=verifySecurityAccess(authorization,securityServiceEndpoint);

		try {
			Person person=personService.getById(Long.valueOf(id)) ;
			return this.fromPersonToPersonResponse(person);
		}catch(EntityNotFoundException e)
		{
			System.err.println("entity not in memory");
			return null;
		}
		  
		
		
	}
	


	// aggiorna persona da id--- U
	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@PutMapping(value="/private/updateMemberOfPeopleGroup/{id}",consumes=MediaType.APPLICATION_JSON_VALUE) //
	public PersonResponse updateMemberOfPeopleGroup(@PathVariable("id") Long id, @RequestBody PersonRequest personRequest) {
		Person pr= personService.save(fromPersonRequestToPerson(personRequest).setId(id));
		
		return fromPersonToPersonResponse(pr);

		
 
	};
	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@DeleteMapping("/private/deleteMemberOfPeopleGroup/{id}") //
	public Long deleteMemberOfPeopleGroup(@PathVariable("id") Long id) {
		
		personService.deleteById(id);
		System.err.println("person deleted");

		return id;

	};
	
	

//*****************************************************GESTIONE ERRORI
	// errore sull'inserimento persona
	@GetMapping("/errorPage")
	public String getMemberOfPeopleGroupErrorPage(Model model, HttpSession httpSession,
			@ModelAttribute("errorMessage") String errorMessageResponse) {

		ViewManager
						.builder()
						.getErrorPage_isVisible(true)
						.build()
						.addAttributeToMap("errorMessage", errorMessageResponse)
						.updateView(httpSession, model);
						
		return "Index";
	}
	
//	+++++++++++++++++++++++++
	private Person fromPersonRequestToPerson(PersonRequest personRequest)
	{
		return Person.builder()
					.id(personRequest.getId())
					.age(personRequest.getAge())
					.name(personRequest.getName())
					.surname(personRequest.getSurname())
					.build();
	}
	
	private PersonResponse fromPersonToPersonResponse (Person p)
	{
		return PersonResponse.builder()
							.id(p.getId())
							.age(p.getAge())
							.name(p.getName())
							.surname(p.getSurname())
							.build();
	}
	
	private boolean verifySecurityAccess(String authorization,String sendToSecurityService)
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

	


}
