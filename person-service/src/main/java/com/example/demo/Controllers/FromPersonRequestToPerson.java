package com.example.demo.Controllers;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Person;
import com.example.demo.model.PersonRequest;

@Mapper(componentModel = "spring")
public interface FromPersonRequestToPerson {
	
	Person perform(PersonRequest personRequest);

}
