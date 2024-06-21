package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
//@Builder
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	
	private String email;
	
	@JsonIgnore
	private String password;
	
	private String role; //single role
	
	private String extraInfo;

	public UserEntity() {
		this.password="";
		this.role="";
		this.extraInfo="";
		
		
	}

	
}
