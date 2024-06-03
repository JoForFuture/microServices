package com.example.demo.Services;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entities.Person;
import com.example.demo.Repositories.PersonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService{
	
	@Autowired
	PersonRepository personRepository;
	
	
	@Autowired
	Person person;

	
	private boolean nameAndSurnameNotEmpty(String surname, String name)
	{
		if(name!=""&&surname!="") {
			return true;
		}else {
			return false;
		}
	}
	
	
	//ricerca con nome cognome 
	public Optional<Person> getByNameAndSurnameIgnoreCase(String surname, String name){
		if(nameAndSurnameNotEmpty(surname, name))
		{
			Predicate<Person> isSameSurname= member->surname.trim().toLowerCase().equals(member.getSurname().toLowerCase());
			Predicate<Person> isSameName= member->name.trim().toLowerCase().equals(member.getName().toLowerCase());
			return
					personRepository
						.findAll()
						.stream()
						.filter(isSameName.and(isSameSurname))
						.findFirst();
		}
		
		return Optional.empty();

	}
	
	

	//recupera con id
	public Optional<Person> getById(Long id) 
	{
				try {
					 
							return personRepository.findById(id);
									//.getReferenceById(id);
					
							
				}catch(IllegalArgumentException e)
				{
					e.printStackTrace();
					return Optional.empty();
				}
				
	
	}
	
	
	public  Person save(Person entity)throws NullPointerException {
		// TODO Auto-generated method stub
		Optional<Person> person=getByNameAndSurnameIgnoreCase(entity.getSurname(), entity.getName());
		if(person.isPresent()) {
			return null;
		};
		//evito l'inserimento senza spazi
		entity.setName(entity.getName().trim());
		entity.setSurname(entity.getSurname().trim());
		return personRepository
							.save(entity);
	}

	
	@Override
	@Transactional(rollbackFor= {SQLException.class})
	public Person update(Long id,Person personFromRequest) throws SQLException{
		Optional<Person> personFromDbOptional=getById(id);
		if(personFromDbOptional.isPresent())
		{
			
			Person personFromDb=personFromDbOptional.get();
			Person personForUpdate=matchNotBlanckFields(personFromDb, personFromRequest);
			Person personUpdated = personRepository.save(personForUpdate);
			
			List<Person> personList=personRepository.findAll().stream()
							.filter(person->person.getName().equals(personUpdated.getName())&&person.getSurname().equals(personUpdated.getSurname()))
							.toList();
			if(personList.size()>1)
			{
				System.err.println("Lista >1 seguirà roolBack");
				throw new SQLException("Duplicated value->not accepted");
			}else
			{
				System.err.println("Inserimento corretto non ci sono altri valori corrispondenti!");

			}
			return personUpdated;
			 
			 
		}
		return null;
	}
	
	
	public Person matchNotBlanckFields(Person personFromDb,Person personFromRequest)
	{
	
		Optional<Person> matched=Optional.of(personFromDb).map(matchedPerson->{
			if(personFromRequest.getSurname()!=null&&!personFromRequest.getSurname().isEmpty()) {
				matchedPerson.setSurname(personFromRequest.getSurname());
			}if(personFromRequest.getName()!=null&&!personFromRequest.getName().isEmpty()) {
				matchedPerson.setName(personFromRequest.getName());
			}if(personFromRequest.getAge()!=-1) {
				matchedPerson.setAge(personFromRequest.getAge());
			}
			return Optional.of(matchedPerson);
		}).get();
		return matched.get();
		
	}
	

	@Override
	public Long deleteById(Long id) {
		Optional<Person> candidateToDelete= this.getById(id);
		if(candidateToDelete.isPresent()) {
			personRepository.deleteById(id);
			return id;
		}else
			return -1l;
	}




	//
	public <S extends Person> List<S> saveAll(Iterable<S> entities){
		return personRepository.saveAll(entities);
	}
	

	
	
	@Override
	public List<Person> findAll() {
		// TODO Auto-generated method stub
		
		return 
				 personRepository.findAll();
	}
	
		@Override
	   public List<Person> findAllSortedByIdReverse() {
	        return personRepository.findAll().stream()
	                        .sorted(Comparator.comparing(Person::getId).reversed())
	                        .collect(Collectors.toList());
	    }
	
	
	@Override
	public Flux<Page<Person>> findAllReactivePageable() {
		// TODO Auto-generated method stub
		
		int pageNumber=0;
		int dimensionPage=10;
		
		        return getPageRecursive(pageNumber,dimensionPage);
				 
	}
	
	
	
	  private Flux<Page<Person>> getPageRecursive(int pageNumber, int dimensionPage) {
		  return Mono.fromCallable(()->personRepository.getPage(pageNumber, dimensionPage)).delayElement(Duration.ofMillis(500))
				  .flatMapMany(p->{
					  if(p.isEmpty()) return Flux.empty();
					  else {
						  return getPageRecursive(pageNumber+1,dimensionPage)
								  .startWith(p);
					  }
				  });
	  }
	
	
	

	@Override
	public List<Person> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return personRepository
							.findAllById(ids);
	}


	



	


	

}
