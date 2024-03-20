package com.example.demo.Controllers;

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
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.Entities.Person;
import com.example.demo.Services.PersonService;
import com.example.demo.model.PersonRequest;
import com.example.demo.model.SessionManagerView;

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
	public String addToPeople(@ModelAttribute PersonRequest personDTOIn, HttpSession session,Model model) {
		
		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
		
		return webClient.post()
				.uri("http://localhost:8082/managePeopleGroup/private/addToPeopleGroup")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(personDTOIn)
                .retrieve()
                .bodyToMono(String.class)
                .block();

	
	}
	
	@PostMapping("/searchPerson")
	public String searchPerson(@ModelAttribute PersonRequest personDTOIn, HttpSession session,Model model) {
		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
		
		return webClient.post()
				.uri("http://localhost:8082/managePeopleGroup/searchPerson")
             .headers(httpHeaders -> httpHeaders.addAll(headers))
             .bodyValue(personDTOIn)
             .retrieve()
             .bodyToMono(String.class)
             .block();

		
}

	// recupera persona da ID---R
	@GetMapping("/getMemberOfPeopleGroup/{id}")
	public String getMemberOfPeopleGroup(@PathVariable String id, 
			HttpSession session, Model model) {

		Person personOut = personService.getById(Long.valueOf(id));
		SessionManagerView sessionManagerView = (SessionManagerView) session.getAttribute("sessionManagerView");
		String stringResponse=(String) sessionManagerView.getAttributesMap()
																		.get("messaggioDinamico") ;


		SessionManagerView
						.builder()
						.getPersonDetailsPage_isVisible(true)
						.build()
						.addAttributeToMap("person", personOut)
						.addAttributeToMap("response", stringResponse)
						.updateView(session, model);
		
		return "Index";
	}

	// aggiorna persona da id--- U
	@PutMapping("/private/updateMemberOfPeopleGroup") //
	public String updateMemberOfPeopleGroup(@ModelAttribute PersonRequest peopleDTO) {
		System.err.println("personRequest:"+peopleDTO);
		Person transitoryPeople = new Person();
		transitoryPeople.setId(peopleDTO.getId()).setName(peopleDTO.getName()).setSurname(peopleDTO.getSurname())
				.setAge(Integer.valueOf(peopleDTO.getAge()));
		personService.save(transitoryPeople);
		System.err.println("transitoryPeople:"+transitoryPeople);

		return "redirect:/managePeopleGroup/getMemberOfPeopleGroup/" + transitoryPeople.getId();

	};
	
	@DeleteMapping("/private/deleteMemberOfPeopleGroup") //
	public String deleteMemberOfPeopleGroup(@ModelAttribute PersonRequest peopleDTO) {
		
		personService.deleteById(peopleDTO.getId());
		System.err.println("person deleted");

		return "redirect:/searchPerson/view";

	};
	
	

//*****************************************************GESTIONE ERRORI
	// errore sull'inserimento persona
	@GetMapping("/errorPage")
	public String getMemberOfPeopleGroupErrorPage(Model model, HttpSession httpSession,
			@ModelAttribute("errorMessage") String errorMessageResponse) {

		SessionManagerView
						.builder()
						.getErrorPage_isVisible(true)
						.build()
						.addAttributeToMap("errorMessage", errorMessageResponse)
						.updateView(httpSession, model);
						
		return "Index";
	}

}
