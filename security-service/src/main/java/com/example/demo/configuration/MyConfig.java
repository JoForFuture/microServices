package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.demo.model.PersonRequest;

@Configuration
public class MyConfig {
	


//	//questo mi serve per i costruttori builder che mi richiedono i parametri dal momento che sono component e devono istanziare gli argomenti
//	@Bean
//	@Primary
//	boolean booleanDefaultValue()
//	{
//	
//		return false;
//	}
	
//	@Bean
//	@Primary
//	PersonRequest personRequest()
//	{
//		return PersonRequest.builder()
//							.age(0)
//							.id(-1l)
//							.name("")
//							.surname("")
//							.build();
//	}
}
