
package com.example.demo.Controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Person;
import com.example.demo.Services.PersonService;
import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;
import com.example.demo.tosecurityservice.ToMyCustomSecurityService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@RestController//ricorda hai cambiato quii!!
@RequestMapping("/")
public class PersonController {

	
	
	@Autowired
	PersonService personService;
	
	
	@Autowired
	FromPersonToPersonResponse fromPersonToPersonResponse;
	
	@Autowired
	FromPersonRequestToPerson fromPersonRequestToPerson;
	
	private static final String securityServiceEndpoint="http://security-service/securityControl/accessPoint";
//
//	@Autowired
//	PersonDTO personDTO;

	@ToMyCustomSecurityService(securityEndpointService=securityServiceEndpoint)
	@GetMapping(path = "/getAll")
	public ResponseEntity<List<Person>> getAll( HttpSession session,Model model) throws NotFoundException {
					
			List<Person> personList =personService.findAll();
			
				 return new ResponseEntity<List<Person>> (personList ,HttpStatus.FOUND);
		
//				return new ResponseEntity<PersonResponse> (HttpStatus.NOT_FOUND);
			

	}
	

	@ToMyCustomSecurityService(securityEndpointService=securityServiceEndpoint)
	@GetMapping(path = "/getByNameAndSurname")
	public ResponseEntity<PersonResponse> getByNameAndSurname( @RequestParam("surname") String surname,@RequestParam("name") String name,HttpSession session,Model model) throws NotFoundException {
					

			Optional<Person> personInMemory=personService.getByNameAndSurnameIgnoreCase(surname,name);
			if (personInMemory.isPresent()) {
				 PersonResponse personResponse=fromPersonToPersonResponse.perform(personInMemory.get());
				 return new ResponseEntity<PersonResponse> (personResponse,HttpStatus.FOUND);}
			else {
				
				return new ResponseEntity<PersonResponse> (HttpStatus.NOT_FOUND);
			}

	}
	
	// recupera persona da ID---R
	@ToMyCustomSecurityService(securityEndpointService=securityServiceEndpoint)
	@GetMapping("/getById")
	public ResponseEntity<PersonResponse> getById(@RequestParam("id") String id,
			HttpSession session, Model model) throws EntityNotFoundException{

		Optional<Person> personInMemory=personService.getById(Long.valueOf(id)) ;
		if (personInMemory.isPresent()) {
			 PersonResponse personResponse=fromPersonToPersonResponse.perform(personInMemory.get());
			 return new ResponseEntity<PersonResponse> (personResponse,HttpStatus.FOUND);}
		else {
			return new ResponseEntity<PersonResponse> (HttpStatus.NOT_FOUND);
		}
		
		
	}
	


	// MediaType.APPLICATION_FORM_URLENCODED_VALUE
	// aggiungi persona--- C
	@ToMyCustomSecurityService(securityEndpointService=securityServiceEndpoint)
	@PostMapping(path = "/private/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addPerson(@RequestBody PersonRequest personRequest, HttpSession session,Model model) {

		Person personFromRequest = fromPersonRequestToPerson.perform(personRequest);
			try {			
				Person person=personService.save(personFromRequest);
				if(person==null) throw new IllegalArgumentException();
				
				return  new ResponseEntity<Long>(person.getId(),HttpStatus.CREATED);
				
				
			}catch(NullPointerException npe)
			{
				npe.printStackTrace(); return new ResponseEntity<Long>(HttpStatus.NOT_ACCEPTABLE);
			}catch(Exception e)
			{
				e.printStackTrace(); return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
			}


	}
	






	// aggiorna persona da id--- U
	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@PutMapping(value="/private/update",consumes=MediaType.APPLICATION_JSON_VALUE) //
	public ResponseEntity<Long> updatePerson(@RequestParam("id") Long id, @RequestBody PersonRequest personRequest) {
		try
		{
			Person pr= personService.update(id, fromPersonRequestToPerson.perform(personRequest));
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
	@ToMyCustomSecurityService(securityEndpointService=PersonController.securityServiceEndpoint)
	@DeleteMapping("/private/delete") //
	public ResponseEntity<Long> deletePerson(@RequestParam("id") Long id) {
		
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
	
	


	


}
