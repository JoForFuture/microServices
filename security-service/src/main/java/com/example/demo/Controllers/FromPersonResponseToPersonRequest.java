package com.example.demo.Controllers;

import org.mapstruct.Mapper;

import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;

@Mapper(componentModel = "spring")
public interface FromPersonResponseToPersonRequest {
	
	PersonRequest perform(PersonResponse personResponse);

}
