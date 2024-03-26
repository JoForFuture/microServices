package com.example.demo.model;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonResponse {
	
	private Long id;
	
	private String name;
	
	private String surname;
	
	private int age;

}
