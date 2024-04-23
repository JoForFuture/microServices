package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.Entities.Person;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;
import com.example.demo.model.ViewManager;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class GatewayNavigationController {
	
	
	@Autowired
	FromPersonResponseToPersonRequest fromPersonResponseToPersonRequest;
	
	
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
//		 ****!!!! NON USARE LA CLASSE ViewManager PERCHè COMPROMETTE LE FUNZIONALITà
//		IN QUESTA SITUAZIONE!!!!
//		DA QUI A.....
		model.addAttribute("formLogin_isVisible", true);
		model.addAttribute("LoginRequest", new LoginRequest());
////		QUI!!
//		Map<String,Object> attributesMap=new HashMap<String,Object>();
//		attributesMap.put("LoginRequest", new LoginRequest());
////		attributesMap.put("person", PersonResponse.builder().build());
////		
//		ViewManager	
//					.builder()
//					.formLogin_isVisible(true)
//					.attributesMap(attributesMap)
//					.build().updateView(session, model);
//////		
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
		
//		PersonRequest personRequest=fromPersonResponseToPersonRequest.perform(p);

		Map<String,Object> attributesMap=new HashMap<String,Object>();
		attributesMap.put("person", p);

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
		
		ViewManager viewManager =null;
		PersonResponse personRetrived =null;
		
		
		try { viewManager = (ViewManager) session.getAttribute("sessionManagerView");}
		catch(IllegalStateException ise) {ise.printStackTrace(); return "redirect:/gestionale/in/view";}
		
		try { personRetrived = (PersonResponse) viewManager.getAttributesMap().get("person");}
		catch(NullPointerException npe) {npe.printStackTrace(); return "redirect:/gestionale/in/view";}
		

		PersonRequest personRequest=fromPersonResponseToPersonRequest.perform(personRetrived);

		Map<String,Object> attributesMap=new HashMap<String,Object>();
		attributesMap.put("person", personRequest);
		
			ViewManager
				.builder()
				.deleteMemberOfPeopleGroup_isVisible(true)
				.attributesMap(attributesMap)
				.build()
					.updateView(session, model);

		
		
		return "Index";


	}
	
	
}
