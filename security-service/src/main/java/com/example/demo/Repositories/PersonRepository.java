package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

}
