package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.example.demo.model.Cane;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
public class PersonMain {
	
	
	

	public static void main(String[] args) {
		SpringApplication.run(PersonMain.class, args);
		System.out.println("Start");
		

	}
	
	@Bean
	CommandLineRunner run(Cane c)
	{
		return (args)->{
			c.Abbaia();
			
		};	
		}
		

}
