package com.example.demo.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
	
	default Page<Person> getPage(int pageNumber,int dimensionPage)

	{
	
		Pageable pageable=PageRequest.of(pageNumber, dimensionPage);
		Page<Person> page=findAll(pageable);
		 
		return page;
	}
	
	

}
