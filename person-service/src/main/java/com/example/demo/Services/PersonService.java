package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.Person;
import com.example.demo.model.PersonRequest;

public interface PersonService {
	
	
	public PersonService nameAndSurnameNotEmpty(Person personIn);
	
	
	public PersonService nameAndSurnameNotEmpty(PersonRequest personDTO);
	
	
	//aggiornamento forzato
	public void sincronyzeInstantUpdateSingleEntity();
	
	
	//salvataggio e aggiornamento forzato singolo
	public void saveAndSincronyzeChangesSingleEntity(Person entity);

	//recupera con id
	public Person getById(Long id);


	//
	public <S extends Person> List<S> saveAll(Iterable<S> entities);
	//ricerca con nome cognome 
	public Optional<Person> findByNameAndSurnameIgnoreCase(PersonRequest personDTOIn);
	
	public  Person save(Person entity) ;

	public List<Person> findAll();

	public List<Person> findAllById(Iterable<Long> ids);

	
	public Optional<Person> findById(Long id);
	
	public Long deleteById(Long id);
//	@Override
//	public boolean existsById(Long id) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public long count() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//

//
//	@Override
//	public void delete(People entity) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//
//	@Override
//	public void deleteAll() {
//		// TODO Auto-generated method stub
//		
//	}
//


	

}
