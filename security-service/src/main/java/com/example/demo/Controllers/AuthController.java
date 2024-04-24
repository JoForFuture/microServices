package com.example.demo.Controllers;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Services.UserService;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.example.demo.security.JwtIssuer;
import com.example.demo.security.UserPrincipal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
	
	private final JwtIssuer jwtIssuer;
	
	private final UserService userService;
	
	private final AuthenticationManager authenticationManager;
	
	
	
	@PostMapping(path="/auth/login")
	public String login(@ModelAttribute @Validated LoginRequest request,HttpSession session,HttpServletRequest servletRequest)
	{

		var authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));


		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		var principal=(UserPrincipal) authentication.getPrincipal();
		
				
		var roles=principal.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
	
		//gli argomenti id, email e lista di ruoli li recupero dal db in base alla ricerca con nome utente e password
		Optional< String> token=Optional.of(jwtIssuer.issue(principal.getUserId(), principal.getEmail() , roles));
		session.setAttribute("Authorization", token.get());

		LoginResponse.builder()
						.accessToken(token.get())
						.build();
		
		  session.setAttribute("Authorization", token.get());
		  System.err.println(token.get());
		
		  return "redirect:/gestionale/in/view";


        

		  
		
	}
	
//	@PostMapping(path="/auth/login")
//	public String login(@ModelAttribute @Validated LoginRequest request,HttpSession session,HttpServletRequest servletRequest)
//	{
//		Optional<UserEntity> userInMemory=userService
//									.findByEmail(request.getEmail());
//		// se non trova l'user richiede l'auth
//		if(userInMemory.isEmpty()) return "redirect:/auth/login"; 
//		UserEntity userInMemoryFound=userInMemory.get();
//		
//		PasswordEncoder encoderDecoder=new BCryptPasswordEncoder();
//		
//		 //se non corrisponde la password richiede l'auth
//		if(!encoderDecoder.matches(request.getPassword(), userInMemoryFound.getPassword())) { return "redirect:/auth/login";}
//
//	
//		//gli argomenti id, email e lista di ruoli li recupero dal db in base alla ricerca con nome utente e password
//		Optional< String> token=Optional.of(jwtIssuer.issue(userInMemoryFound.getUserId(), userInMemoryFound.getEmail() , List.of(userInMemoryFound.getRole())));
//		session.setAttribute("Authorization", token.get());
//
//		LoginResponse.builder()
//						.accessToken(token.get())
//						.build();
//
//
//        
//		  return "redirect:/gestionale/in/view";
////        return "redirect:"+pathAfterAuth;
//
//
////		  session.setAttribute("Authorization", loginResponse.getAccessToken());
////		
////		  return loginResponse.getSuccessUrl();
//		  
//		
//	}
	
	
	
	
	@PostMapping("/auth/logout")
	public String logout( HttpSession session)
	{
		session.setAttribute("Authorization", "");
		SecurityContextHolder.clearContext();
		

		return "redirect:/index";
	}
	
	






	
}
