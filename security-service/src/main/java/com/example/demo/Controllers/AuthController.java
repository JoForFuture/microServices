package com.example.demo.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entities.UserEntity;
import com.example.demo.Services.UserService;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.example.demo.security.JwtIssuer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
	
	private final JwtIssuer jwtIssuer;
	
	private final UserService userService;
	
	
	
	
	@PostMapping(path="/auth/login")
	public String login(@ModelAttribute @Validated LoginRequest request,HttpSession session,HttpServletRequest servletRequest)
	{
		Optional<UserEntity> userInMemory=userService
									.getByEmail(request.getEmail());
		// se non trova l'user richiede l'auth
		if(userInMemory.isEmpty()) return "redirect:/auth/login"; 
		UserEntity userInMemoryFound=userInMemory.get();
		
		PasswordEncoder encoderDecoder=new BCryptPasswordEncoder();
		
		 //se non corrisponde la password richiede l'auth
		if(!encoderDecoder.matches(request.getPassword(), userInMemoryFound.getPassword())) { return "redirect:/auth/login";}

	
		//gli argomenti id, email e lista di ruoli li recupero dal db in base alla ricerca con nome utente e password
		Optional< String> token=Optional.of(jwtIssuer.issue(userInMemoryFound.getUserId(), userInMemoryFound.getEmail() , List.of(userInMemoryFound.getRole())));
		session.setAttribute("Authorization", token.get());

		LoginResponse.builder()
						.accessToken(token.get())
						.build();
        System.err.println(token.get());

		  return "redirect:/gestionale/in/view";


//		  session.setAttribute("Authorization", loginResponse.getAccessToken());
//		
//		  return loginResponse.getSuccessUrl();
		  
		
	}
	
	
	@PostMapping("/auth/logout")
	public String logout( HttpSession session)
	{
		session.setAttribute("Authorization", "");
		SecurityContextHolder.clearContext();
		

		return "redirect:/index";
	}
	
	






	
}
