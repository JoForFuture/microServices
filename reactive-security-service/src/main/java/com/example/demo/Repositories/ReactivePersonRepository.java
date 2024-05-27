package com.example.demo.Repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Person;

@Repository
public interface ReactivePersonRepository extends ReactiveCrudRepository<Person,Long>  {
	
	

}
