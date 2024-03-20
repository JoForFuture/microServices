package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Entities.Person;
import com.example.demo.model.PersonRequest;
import com.example.demo.model.SessionManagerView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class NavigationController {
	
	
	
	@GetMapping("/index")
	public String indexView(Model model, HttpSession session ) {
	
		SessionManagerView	
						.builder()
						.indexPage_isVisible(true)
						.build()
						.updateView(session, model);
		
		
		return "Index";
	}
	
	@GetMapping("/gestionale/in/view")
	public String gestionaleIn(Model model, HttpSession session )
	{
		SessionManagerView	
						.builder()
						.gestionaleIn_isVisible(true)
						.build()
						.updateView(session, model);

		return "Index";
	}
	
	
	@GetMapping("private/addToPeopleGroup/view")
	public String addToPeopleGroupView(Model model, HttpSession session,PersonRequest personDTO) {
		//aggiungo l'oggetto per lo scambio fatch
		
		SessionManagerView
						.builder()
						.formAddToPeopleGroup_isVisible(true)
						.build()
						.addAttributeToMap("person", personDTO)
						.updateView(session, model);
						
		
		return "Index";
	}
	
	@GetMapping("/searchPerson/view")
	public String searchPerson(Model model, HttpSession session,PersonRequest personDTO) {
		//aggiungo l'oggetto per lo scambio fatch
		
		SessionManagerView
						.builder()
						.formSearchPerson_isVisible(true)
						.build()
						.addAttributeToMap("person", personDTO)
						.updateView(session, model);
						
		
		return "Index";
	}

	@GetMapping("/private/updateMemberOfPeopleGroup/view")
	public String updateMemberOfPeopleGroupView(Model model, HttpSession session)
	{
		

		SessionManagerView sessionManagerView=(SessionManagerView) session.getAttribute("sessionManagerView");
		Person p=(Person) sessionManagerView.getAttributesMap().get("person");

		SessionManagerView
						.builder()
						.updateMemberOfPeopleGroup_isVisible(true)
						.build()
						.addAttributeToMap("person", p)
						.updateView(session, model);
		

		return "Index";
	}
	
	@GetMapping("/private/deleteMemberOfPeopleGroup/view")
	public String deleteMemberOfPeopleGroupView(Model model, HttpSession session)
	{
		

		SessionManagerView sessionManagerView=(SessionManagerView) session.getAttribute("sessionManagerView");
		Person p=(Person) sessionManagerView.getAttributesMap().get("person");

		SessionManagerView
						.builder()
						.deleteMemberOfPeopleGroup_isVisible(true)
						.build()
						.addAttributeToMap("person", p)
						.updateView(session, model);
		

		return "Index";
	}
	
}
