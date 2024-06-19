package com.example.demo.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtDecoder jwtDecoder;
	
	private final JwtToPrincipalConverter jwtToPrincipalConverter;	
	
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//fine****	
		//lo faccio due volte: 1 lo prendo dalla request e se non lo trovo lo prendo dalla sessione
		try {

			extractTokenFromRequest(request)
				.map(jwtDecoder::decode)
				.map(jwtToPrincipalConverter::convert)
				.map(UserPrincipalAuthenticationToken::new)
				.ifPresentOrElse(authentication->
				{
					SecurityContextHolder.getContext().setAuthentication(authentication);
					System.err.println("auth presente");
				},()->{
				System.err.println("auth not present");
				
			});
			
			
//			.ifPresent(authentication->
//			{
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//				System.err.println("auth presente");
//			});
				 
			

			
		}catch(Exception nSEE)
		{ 
			nSEE.printStackTrace();
			System.err.println("Problemi di auth");
		}
//	
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
