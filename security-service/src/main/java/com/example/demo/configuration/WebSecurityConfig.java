package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.CustomUserDetailService;
import com.example.demo.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final CustomUserDetailService customUderDetailsService;
	
	
	
	@Bean
	public SecurityFilterChain applicationSecurity(HttpSecurity http)throws Exception{
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .cors(cors->cors.disable())
                .csrf(crsf->crsf.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(login -> login.loginPage("/formLogin/view"))
     
                .securityMatcher("/**")
                .authorizeHttpRequests(registry -> registry
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/formLogin/view").permitAll()
                                .requestMatchers("/auth/login").permitAll() 
//                                .requestMatchers("/gestionale/in/view").permitAll()
//                                .requestMatchers("/api/view/person/getAllReactive").permitAll()
//                              .requestMatchers("/private/updateMemberOfPeopleGroup/view").permitAll()
                              .requestMatchers("/securityControl/accessPoint").permitAll()
                                .requestMatchers("/private/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                );
        
        
        
		
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception
	{
		
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(customUderDetailsService)
				.passwordEncoder(passwordEncoder())
				.and()
				.build();
				
	}
}
