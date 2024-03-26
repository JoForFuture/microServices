package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Entities.Person;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;
import com.example.demo.model.ViewManager;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class NavigationController {
	
	
	
	@GetMapping("/index")
	public String indexView(Model model, HttpSession session ) {
	
		ViewManager	
						.builder()
						.indexPage_isVisible(true)
						.build()
							.updateView(session, model);
		
		
		return "Index";
	}
	
	@GetMapping("/gestionale/in/view")
	public String gestionaleIn(Model model, HttpSession session )
	{
		ViewManager	
						.builder()
						.gestionaleIn_isVisible(true)
						.build()
							.updateView(session, model);

		return "Index";
	}
	
	@GetMapping("/formLogin/view")
	public String formLoginView(Model model, HttpSession session)
	{	
//		ViewManager	
//						.builder()
//						.formLogin_isVisible(true)
//						.build()
//						.addAttributeToMap("LoginRequest", new LoginRequest())
//						.updateView(session, model);
//		 ****!!!! NON USARE LA CLASSE ViewManager PERCHè COMPROMETTE LE FUNZIONALITà
//		IN QUESTA SITUAZIONE!!!!
		model.addAttribute("formLogin_isVisible", true);
		model.addAttribute("LoginRequest", new LoginRequest());
		
		return "Index";
	}
	
	@GetMapping("private/addToPeopleGroup/view")
	public String addToPeopleGroupView(Model model, HttpSession session,PersonRequest personRequest) {
		//aggiungo l'oggetto per lo scambio fatch
		Map<String,Object> attributesMap=new HashMap<String,Object>();
		attributesMap.put("person", personRequest);
		
		ViewManager
						.builder()
						.formAddToPeopleGroup_isVisible(true)
						.attributesMap(attributesMap)
						.build()
							.updateView(session, model);
						
		
		return "Index";
	}
	
	@GetMapping("/searchPerson/view")
	public String searchPerson(Model model, HttpSession session,PersonRequest personRequest) {
		//aggiungo l'oggetto per lo scambio fatch
		Map<String,Object> attributesMap=new HashMap<String,Object>();
		attributesMap.put("person", personRequest);
		
		ViewManager
						.builder()
						.formSearchPerson_isVisible(true)
						.attributesMap(attributesMap)
						.build()
							.updateView(session, model);
						
		
		return "Index";
	}

	@GetMapping("/private/updateMemberOfPeopleGroup/view")
	public String updateMemberOfPeopleGroupView(Model model, HttpSession session)
	{
		

		ViewManager viewManager=(ViewManager) session.getAttribute("sessionManagerView");
		PersonResponse p=(PersonResponse) viewManager.getAttributesMap().get("person");
		PersonRequest personRequest=fromPersonResponseToPersonRequest(p);

		Map<String,Object> attributesMap=new HashMap<String,Object>();
		attributesMap.put("person", personRequest);

		ViewManager
						.builder()
						.updateMemberOfPeopleGroup_isVisible(true)
						.attributesMap(attributesMap)
						.build()
							.updateView(session, model);
		

		return "Index";
	}
	
	@GetMapping("/private/deleteMemberOfPeopleGroup/view")
	public String deleteMemberOfPeopleGroupView(Model model, HttpSession session)
	{
		

		ViewManager viewManager=(ViewManager) session.getAttribute("sessionManagerView");
		PersonResponse p=(PersonResponse) viewManager.getAttributesMap().get("person");

		ViewManager
						.builder()
						.deleteMemberOfPeopleGroup_isVisible(true)
						.build()
						.addAttributeToMap("person", p)
							.updateView(session, model);
		

		return "Index";
	}
	
	
//	++++++++++++
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
	
	private PersonRequest fromPersonResponseToPersonRequest (PersonResponse p)
	{
		return PersonRequest.builder()
							.id(p.getId())
							.age(p.getAge())
							.name(p.getName())
							.surname(p.getSurname())
							.build();
	}

	
}
