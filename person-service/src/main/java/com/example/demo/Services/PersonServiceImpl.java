package com.example.demo.Services;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Person;
import com.example.demo.Repositories.PersonRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonServiceImpl implements PersonService{
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	Person person;

	
	public PersonServiceImpl nameAndSurnameNotEmpty(Person personIn)
	{
		if(personIn.getSurname()!=""&&personIn.getName()!="") {
			return this;
		}else {
			return null;
		}
	}
	
//	public PersonServiceImpl nameAndSurnameNotEmpty(PersonRequest personDTO)
//	{
//		if(personDTO.getSurname()!=""&&personDTO.getName()!="") {
//			return this;
//		}else {
//			return null;
//		}
//	}
	
	
	//aggiornamento forzato
	public void sincronyzeInstantUpdateSingleEntity()
	{
		personRepository.flush();
	}
	
	//salvataggio e aggiornamento forzato singolo
	public void saveAndSincronyzeChangesSingleEntity(Person entity)
	{
		personRepository.saveAndFlush(entity);
	}

	//recupera con id
	public Person getById(Long id) throws EntityNotFoundException
	{
				try{
					return 
							personRepository.getReferenceById(id);
				}catch(Exception e)
				{
					e.printStackTrace();
					return  null;
				}
				
	
	}

	//
	public <S extends Person> List<S> saveAll(Iterable<S> entities){
		return personRepository.saveAll(entities);
	}
	
	//ricerca con nome cognome 
	public Optional<Person> findByNameAndSurnameIgnoreCase(Person personDTOIn){
		Predicate<Person> isSameName= member->personDTOIn.getName().toLowerCase().equals(member.getName().toLowerCase());
		Predicate<Person> isSameSurname= member->personDTOIn.getSurname().toLowerCase().equals(member.getSurname().toLowerCase());
		return
				personRepository
					.findAll()
					.stream()
					.filter(isSameName.and(isSameSurname))
					.findFirst();
	

	}
	
	
	@Override
	public List<Person> findAll() {
		// TODO Auto-generated method stub
		return 
				personRepository.findAll();
	}

	@Override
	public List<Person> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return personRepository
							.findAllById(ids);
	}

	
	public  Person save(Person entity) {
		// TODO Auto-generated method stub
		return personRepository
							.save(entity);
	}

	@Override
	public Optional<Person> findById(Long id) {
		// TODO Auto-generated method stub
		return personRepository
							.findById(id);
	}
	@Override
	public Long deleteById(Long id) {
		personRepository.deleteById(id);;
		return id;
	}

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



	

}
