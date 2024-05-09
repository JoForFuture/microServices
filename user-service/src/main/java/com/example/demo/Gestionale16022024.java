package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Gestionale16022024 {
	
	
	

	public static void main(String[] args) {
		SpringApplication.run(Gestionale16022024.class, args);
		System.out.println("Start");
		

	}
	
//	@Bean
//	CommandLineRunner run( )
//	{
//		return (args)->{
//			
////			PasswordEncoder codifica=new BCryptPasswordEncoder();
////			String stringa =codifica.encode("benemale");
////			UserEntity userEntity= UserEntity
////					.builder()
////					.email("laprima@chetiviene.com")
////					.password(stringa)
////					.role("ROLE_USER")
////					.build();
////
////
////			userService.save(userEntity);
////			
////		String paginaIndex=webClient.get()
////					.uri("http://localhost:8082/index")
////					.retrieve()
////					.bodyToMono(String.class)
////					.block();
////		
////		System.err.println("lallalalall"+paginaIndex);
//			
//		};	
//		}
		

}
