package com.example.demo.Services;

import org.springframework.data.domain.Page;

import com.example.demo.Entities.Person;

import reactor.core.publisher.Flux;

public interface ReactivePersonService {
	
	public Flux<Person> findAll();



}
