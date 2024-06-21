package com.example.demo.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Setter;

@Component
@Data
public class PersonRequest {
	
public PersonRequest() {};
	
	public PersonRequest(Long id, String surname, String name, int age) {
		this.id = id;
		this.surname = surname;
		this.name = name;
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
