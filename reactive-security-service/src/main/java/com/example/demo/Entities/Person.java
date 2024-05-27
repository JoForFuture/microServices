package com.example.demo.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Component;

import lombok.Builder;

@Component
@Builder
@Table("Person")
public class Person {
	
	public Person() {};
	
	
	public Person(Long id, String name, String surname, int age) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.age = age;
	}


	@Id
	private Long id;
	
	private String name;
	
	private String surname;
	
	private int age;

	public Long getId() {
		return id;
	}

	public Person setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Person setName(String name) {
		this.name = name;
		return this;
	}

	public String getSurname() {
		return surname;
	}

	public Person setSurname(String surname) {
		this.surname = surname;
		return this;
	}

	public int getAge() {
		return age;
	}

	public Person setAge(int age) {
		this.age = age;
		return this;
	}

	@Override
	public String toString() {
		return "Getted: [id=" + id + ", name=" + name + ", surname=" + surname + ", age=" + age + "]";
	}

}
