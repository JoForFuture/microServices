package com.example.demo.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/securityControl")
@RequiredArgsConstructor
public class ExternalAuthController {
	
	

	@PostMapping(value="/accessPoint")
	public Boolean securityPass()
	{
		System.err.println("Secured by security-service for 'all roles users' ");
		return true;
	}
	
	
	@PostMapping(value="/accessPoint/admin")
	public Boolean securityPassAdmin()
	{
		System.err.println("Secured by security-service for 'admin role users' ");

		return true;
	}
	
	@PostMapping(value="/accessPoint/user")
	public Boolean securityPassUser()
	{
		System.err.println("Secured by security-service for 'user role users' ");

		return true;
	}

  
	
}


