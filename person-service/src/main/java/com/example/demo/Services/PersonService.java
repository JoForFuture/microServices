package com.example.demo.Services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.data.domain.Page;

import com.example.demo.Entities.Person;

import reactor.core.publisher.Flux;

public interface PersonService {
	
	
	
	public Optional<Person> getByNameAndSurnameIgnoreCase(String surname,String name);

	
	
	//recupera con id
	public Optional<Person> getById(Long id);


	public  Person save(Person entity) ;
	
	
	public Person update(Long id,Person person) throws SQLException;

	//
	public <S extends Person> List<S> saveAll(Iterable<S> entities);
	//ricerca con nome cognome 
	
	public List<Person> findAll();
	
	public Flux<Page<Person>> findAllReactivePageable();
	
	

	public List<Person> findAllById(Iterable<Long> ids);

	
	
	public Long deleteById(Long id);



	public List<Person> findAllSortedByIdReverse();







//


	

}
