package com.example.demo.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

//	@Autowired
//	PersonDTO personDTO;

	public PersonController() { 
	};

	// aggiungi persona--- C
	@PostMapping(path = "/private/addToPeopleGroup", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String addToPeople(@ModelAttribute PersonRequest personDTOIn, HttpSession session,Model model) {
		try {

			 Optional<Person> personGetted = personService.nameAndSurnameNotEmpty(personDTOIn)
					.findByNameAndSurnameIgnoreCase(personDTOIn);
			 
			if (personGetted.isPresent()) {
				SessionManagerView
								.builder()
								.getPersonDetailsPage_isVisible(true)
								.build()
								.addAttributeToMap("messaggioDinamico", "esiste già una persona con questo nome e cognome")
								.updateView(session, model);
				Long idTransitory = personGetted.get().getId();

				return "redirect:/managePeopleGroup/getMemberOfPeopleGroup/" + idTransitory;
			} else {
				

				Person transitoryPerson = Person
												.builder()
												.name(personDTOIn.getName())
												.surname(personDTOIn.getSurname())
												.age(personDTOIn.getAge())
												.build();
					
				Long personId = personService.save(transitoryPerson).getId();
				
				// passo l'id appena recuperato
				SessionManagerView	
								.builder()
								.getPersonDetailsPage_isVisible(true)
								.build()
								.addAttributeToMap("messaggioDinamico", "inserimento effettuato con successo!")
								.updateView(session, model);

				return "redirect:/managePeopleGroup/getMemberOfPeopleGroup/" + personId;// + "/" +
																										// URLEncoder.encode(pathResponse,
																										// StandardCharsets.UTF_8);//"group
																										// People was
																										// updated";
			}
		} catch (Exception e) {
				String errorMessage = e.getMessage();// e.printStackTrace();
				SessionManagerView
								.builder()
								.getErrorPage_isVisible(true)
								.build()
								.addAttributeToMap("messaggioDinamico", errorMessage)
								.updateView(session, model);
			
			return "redirect:/managePeopleGroup/errorPage";// + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
		}

	}
	
	@PostMapping("/searchPerson")
	public String searchPerson(@ModelAttribute PersonRequest personDTOIn, HttpSession session,Model model) {
		
			 Optional<Person> personGetted = personService.nameAndSurnameNotEmpty(personDTOIn)
					.findByNameAndSurnameIgnoreCase(personDTOIn);
			 
			if (personGetted.isPresent()) {

				SessionManagerView
								.builder()
								.getPersonDetailsPage_isVisible(true)
								.build()
								.addAttributeToMap("messaggioDinamico", "persona trovata")
								.updateView(session, model);
				Long idTransitory = personGetted.get().getId();

				return "redirect:/managePeopleGroup/getMemberOfPeopleGroup/" + idTransitory;
			}else {
			String errorMessage="persona non trovata";
			

			SessionManagerView
							.builder()
							.getErrorPage_isVisible(true)
							.build()
							.addAttributeToMap("errorMessage", errorMessage)
							.updateView(session, model)
						
							;
		return "Index";}

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
