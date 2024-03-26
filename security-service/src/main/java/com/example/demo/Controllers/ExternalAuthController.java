package com.example.demo.Controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.IncorrectClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.Services.UserService;
import com.example.demo.configuration.JwtProperties;
import com.example.demo.security.JwtDecoder;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/securityControl")
@RequiredArgsConstructor
public class ExternalAuthController {
	
	private final JwtDecoder jwtDecoder;
	private final JwtProperties properties;
	private final UserService userService;

	
	@PostMapping(value="/accessPoint",consumes=MediaType.APPLICATION_JSON_VALUE)
	public Boolean securityPass(@RequestHeader("Authorization") String authorization, HttpSession session)
	{
//		session.setAttribute("Authorization", authorization);
		//decodifico il token
		try {
		DecodedJWT jwt=jwtDecoder.decode(authorization);
		return true;
		}catch(AlgorithmMismatchException a)
		{
			a.printStackTrace();
			
		}catch(SignatureVerificationException s)
		{
			s.printStackTrace();

		}catch(TokenExpiredException t)
		{
			t.printStackTrace();

		}catch(MissingClaimException m)
		{
		m.printStackTrace();	

		}catch(IncorrectClaimException i)
		{
			i.printStackTrace();

		}catch(JWTVerificationException j)
		{
			j.printStackTrace();

		}
		
		return false;

		//comparo la signature con quella inserita
	
		//se è uguale a 0 vuol dire che le signature sono uguali


			//prendo la mail e confronto anche quella
//			String email=jwt.getClaim("e").asString();
//			Optional<UserEntity> user=userService.getByEmail(email);
//			System.err.println(user.get());
	
		

	}

  
	
}


