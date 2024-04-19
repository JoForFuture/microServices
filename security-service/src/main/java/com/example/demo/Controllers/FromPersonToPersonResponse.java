package com.example.demo.Controllers;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Person;
import com.example.demo.model.PersonResponse;

@Mapper(componentModel = "spring")
public interface FromPersonToPersonResponse {
	
	PersonResponse  perform(Person person);

}
