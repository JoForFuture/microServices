package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class Gestionale16022024 {
	
	
	

	public static void main(String[] args) {
		SpringApplication.run(Gestionale16022024.class, args);
		System.out.println("Start");
		

	}
	
//	@Bean
//	CommandLineRunner run( )
//	{
//		
//		}
		

}
