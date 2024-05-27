package com.example.demo.Services;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Person;
import com.example.demo.Repositories.ReactivePersonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactivePersonServiceImpl implements ReactivePersonService{
	
	@Autowired
	ReactivePersonRepository reactivePersonRepository;
	
	
	@Autowired
	Person person;


	@Override
	public Flux<Person> findAll() {
		// TODO Auto-generated method stub
		return reactivePersonRepository.findAll();
	}

	

}
