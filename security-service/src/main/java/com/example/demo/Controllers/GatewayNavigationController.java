package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;
import com.example.demo.model.ViewManager;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor    
@RequestMapping("/")
public class GatewayNavigationController {
	
	@Autowired
	FromPersonResponseToPersonRequest fromPersonResponseToPersonRequest;
	
	@Autowired
	private final ReactorLoadBalancerExchangeFilterFunction lbFunction;
	
	
	
	
	
	@GetMapping(value="/index")
	public String indexView(Model model) {
	
		ViewManager	
						.builder()
						.indexPage_isVisible(true)
						.build()
							.updateView( model);
		
		
		return "Index";
	}
	
	
	
	@GetMapping(value="/gestionale/in/view")
	public String gestionaleIn(@RequestHeader("Authorization") String Authorization,Model model)//
	{
 
		
		ViewManager myViewManager=ViewManager	
						.builder()
						.gestionaleIn_isVisible(true)
						.build()
							.updateView( model);
		
		model.addAttribute("myViewManager",myViewManager);

		return "Index";
	} 
	
	@GetMapping("/beforeLogin/view")
	public String beforeLoginView(Model model)
	{	

		
		ViewManager
						.builder()
						.beforeLoginPage_isVisible(true)
						.build()
							.updateView( model);
						
		
		return "Index";
		

	
	}
	
	@GetMapping("/formLogin/view")
	public String formLoginView(Model model)
	{	

		model.addAttribute("formLogin_isVisible", true);
		model.addAttribute("LoginRequest", new LoginRequest());

		return "Index";
	}
	
	@GetMapping("/private/addToPeopleGroup/view")
	public String addToPeopleGroupView(Model model) {
		//aggiungo l'oggetto per lo scambio fatch
		Map<String,Object> attributesMap=new HashMap<String,Object>();
		attributesMap.put("person", new PersonRequest());
		
		ViewManager
						.builder()
						.formAddToPeopleGroup_isVisible(true)
						.attributesMap(attributesMap)
						.build()
							.updateView( model);
						
		
		return "Index";
	}
	
	@GetMapping("/searchPerson/view")
	public String searchPerson(Model model) {
		Map<String,Object> attributesMap=new HashMap<String,Object>();
		attributesMap.put("person", new PersonRequest());
		
		ViewManager
						.builder()
						.formSearchPerson_isVisible(true)
						.attributesMap(attributesMap)
						.build()
							.updateView( model);
						
		
		return "Index";
	}
	

	@GetMapping(value="/private/updateMemberOfPeopleGroup/view")
	public String updateMemberOfPeopleGroupView(@RequestHeader("Authorization") String authToken,Model model,@RequestParam("id") String id)
	{
				
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", authToken);
	
		PersonResponse personResponse=WebClient.builder()
												.filter(lbFunction)
												.build()
												.get()
												.uri(uribuilder -> uribuilder.scheme("http").host("person-service").path("/getById").queryParam("id",id)
										 				.build())
												.headers(httpHeaders->httpHeaders.addAll(headers))
												.retrieve()
												.toEntity(PersonResponse.class)
												.block()
												.getBody();
		
		PersonRequest personRequest=fromPersonResponseToPersonRequest.perform(personResponse);
	
		
		
		Map<String,Object> attributesMap=new HashMap<String,Object>();
		attributesMap.put("person", personRequest);

		ViewManager
						.builder()
						.updateMemberOfPeopleGroup_isVisible(true)
						.attributesMap(attributesMap)
						.build()
							.updateView(model);
		

		return "Index";
	}
	
	@GetMapping("/private/deleteMemberOfPeopleGroup/view")
	public String deleteMemberOfPeopleGroupView(@RequestHeader("Authorization") String authToken,Model model,@RequestParam ("id") String id)
	{

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", authToken);
		
		PersonResponse personResponse=WebClient.builder()
				.filter(lbFunction)
				.build()
				.get()
				.uri(uribuilder -> uribuilder.scheme("http").host("person-service").path("/getById").queryParam("id",id)
		 				.build())
				.headers(httpHeaders->httpHeaders.addAll(headers))
				.retrieve()
				.toEntity(PersonResponse.class)
				.block()
				.getBody();
		
		PersonRequest personRequest=fromPersonResponseToPersonRequest.perform(personResponse);

		Map<String,Object> attributesMap=new HashMap<String,Object>();
		attributesMap.put("person", personRequest);
		
			ViewManager
				.builder()
				.deleteMemberOfPeopleGroup_isVisible(true)
				.attributesMap(attributesMap)
				.build()
					.updateView( model);    

		
		
		return "Index";

          
	}
	
	
}
