
package com.example.demo.Controllers;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@RestController//ricorda hai cambiato quii!!
@RequestMapping("/person")
public class PersonController {

	
	
	@Autowired
	PersonService personService;
	
	@Autowired
	WebClient webClient;
	
	@Autowired
	FromPersonToPersonResponse fromPersonToPersonResponse;
	
	@Autowired
	FromPersonRequestToPerson fromPersonRequestToPerson;
	
	private static final String securityServiceEndpoint="http://localhost:8081/securityControl/accessPoint";
//
//	@Autowired
//	PersonDTO personDTO;


//	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@GetMapping(path = "get/{surname}/{name}")
	public ResponseEntity<PersonResponse> getByNameAndSurname( @PathVariable("surname") String surname,@PathVariable("name") String name,HttpSession session,Model model) {
					

			Optional<Person> personInMemory=personService.getByNameAndSurnameIgnoreCase(surname,name);
			if (personInMemory.isPresent()) {
				 PersonResponse personResponse=fromPersonToPersonResponse.perform(personInMemory.get());
				 return new ResponseEntity<PersonResponse> (personResponse,HttpStatus.FOUND);}
			else {
				return new ResponseEntity<> (HttpStatus.NOT_FOUND);
			}

	}
	
	// recupera persona da ID---R
//	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@GetMapping("/get/{id}")
	public ResponseEntity<PersonResponse> getById(@PathVariable("id") String id,
			HttpSession session, Model model) throws EntityNotFoundException{

		Optional<Person> personInMemory=personService.getById(Long.valueOf(id)) ;
		if (personInMemory.isPresent()) {
			 PersonResponse personResponse=fromPersonToPersonResponse.perform(personInMemory.get());
			 return new ResponseEntity<PersonResponse> (personResponse,HttpStatus.FOUND);}
		else {
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
		}
		
		
	}
	


	// MediaType.APPLICATION_FORM_URLENCODED_VALUE
	// aggiungi persona--- C
//	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@PostMapping(path = "/private/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addPerson(@RequestBody PersonRequest personRequest, HttpSession session,Model model) {

		Person personFromRequest = fromPersonRequestToPerson.perform(personRequest);
			try {				 
				return  new ResponseEntity<Long>(personService.save(personFromRequest)
															.getId(),HttpStatus.CREATED);
															
			}catch(Exception e)
			{
				e.printStackTrace(); return new ResponseEntity<Long>(-1l,HttpStatus.BAD_REQUEST);
			}


	}
	






	// aggiorna persona da id--- U
//	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@PutMapping(value="/private/update/{id}",consumes=MediaType.APPLICATION_JSON_VALUE) //
	public ResponseEntity<Long> updatePerson(@PathVariable("id") Long id, @RequestBody PersonRequest personRequest) {
		try
		{
			Person pr= personService.update(id, fromPersonRequestToPerson.perform(personRequest));
//			fromPersonToPersonResponse.perform(pr);
			if(pr==null)return new ResponseEntity<Long>(-1l,HttpStatus.NOT_FOUND);

				return new ResponseEntity<Long>(id,HttpStatus.OK);
		}catch(NullPointerException e)
		{
			e.printStackTrace();
			return  new ResponseEntity<>(-1l,HttpStatus.NO_CONTENT);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  new ResponseEntity<>(-1l,HttpStatus.BAD_REQUEST);

		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  new ResponseEntity<>(-1l,HttpStatus.BAD_REQUEST);

		}

	
 
	};
//	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@DeleteMapping("/private/delete/{id}") //
	public ResponseEntity<Long> deletePerson(@PathVariable("id") Long id) {
		
		try {
			if(personService.deleteById(id)==-1){
				return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else {
				System.err.println("person deleted");
				return new ResponseEntity<Long>(id,HttpStatus.OK);
			}
			}catch(Exception e)
		{
			e.printStackTrace();
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
		

		

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
//	private Person FromPersonRequestToPerson(PersonRequest personRequest)
//	{
//		return Person.builder()
//					.id(personRequest.getId())
//					.age(personRequest.getAge())
//					.name(personRequest.getName())
//					.surname(personRequest.getSurname())
//					.build();
//	}
	
//	private PersonResponse fromPersonToPersonResponse (Person p)
//	{
//		return PersonResponse.builder()
//							.id(p.getId())
//							.age(p.getAge())
//							.name(p.getName())
//							.surname(p.getSurname())
//							.build();
//	}
	


	


}
