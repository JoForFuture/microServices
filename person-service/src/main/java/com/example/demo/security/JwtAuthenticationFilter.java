package com.example.demo.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtDecoder jwtDecoder;
	
	private final JwtToPrincipalConverter jwtToPrincipalConverter;	
	
	private final HttpSession session;
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

//		System.err.println( "***************"+request.getRequestURL());
		Optional<String> tokenNullable = Optional.empty();
		try {
//			System.err.println(	"entro in pre con path: " + request.getRequestURI() + session.getAttribute("Authorization"));
			tokenNullable=Optional.ofNullable((String) session.getAttribute("Authorization"));

		} catch (Exception e1) {
			System.err.println("JwtAuthenticationFilter.doFilterInternal.catch0");
		}

	//fine****	
		//lo faccio due volte: 1 lo prendo dalla request e se non lo trovo lo prendo dalla sessione
		try {

			extractTokenFromRequest(request)
				.map(jwtDecoder::decode)
				.map(jwtToPrincipalConverter::convert)
				.map(UserPrincipalAuthenticationToken::new)
				.ifPresent(authentication->SecurityContextHolder.getContext().setAuthentication(authentication));

		}catch(Exception e)
		{ 
			System.err.println("JwtAuthenticationFilter.doFilterInternal.catch1");
		}
//		}
//			se non trovo il token nella richiesta lo cerco nella sessione
		   try{

			   tokenNullable .map(jwtDecoder::decode).get().getExpiresAtAsInstant().isBefore(Instant.now());
			   tokenNullable
				.map(jwtDecoder::decode)
				.map(jwtToPrincipalConverter::convert)
				.map(UserPrincipalAuthenticationToken::new)
				.ifPresent(authentication->SecurityContextHolder.getContext().setAuthentication(authentication));

		   }
			catch(Exception e)

		   {
				System.err.println("JwtAuthenticationFilter.doFilterInternal.catch2");
		   }
//			if (true) {
//				System.err.println( Optional.ofNullable(token).map(jwtDecoder::decode).get().getExpiresAtAsInstant());
//				System.err.println("dentro il token check");
//				
//			}

		   

			filterChain.doFilter(request, response);
		
	}
	
	private Optional<String> extractTokenFromRequest(HttpServletRequest request){
		
		var token = request.getHeader("Authorization");
		if(StringUtils.hasText(token)&&token.startsWith("Bearer ")) {
			return Optional.of(token.substring(7));
		}
		
		return Optional.empty();

	}
	

	


}
