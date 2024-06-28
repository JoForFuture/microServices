package com.example.demo.model;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonResponse {
	
	private Long id;
	
	private String name;
	
	private String surname;
	
	private int age;
	
	public PersonResponse(Long id, String name, String surname, int age) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.age = age;
	}


}
