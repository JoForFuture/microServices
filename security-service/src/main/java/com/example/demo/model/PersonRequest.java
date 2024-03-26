package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonRequest {
	
public PersonRequest() {};
	
	public PersonRequest(Long id, String name, String surname, int age) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.age = age;
	}

	private Long id;
	
	private String name;
	
	private String surname;
	
	private int age;

	@Override
	public String toString() {
		return "Added: [id=" + id + ", name=" + name + ", surname=" + surname + ", age=" + age + "]";
	}

	
	

}
