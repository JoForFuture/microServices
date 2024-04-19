package com.example.demo.Controllers;

import com.example.demo.Entities.Person;
import com.example.demo.model.PersonResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-19T12:24:59+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class FromPersonToPersonResponseImpl implements FromPersonToPersonResponse {

    @Override
    public PersonResponse perform(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonResponse.PersonResponseBuilder personResponse = PersonResponse.builder();

        personResponse.id( person.getId() );
        personResponse.name( person.getName() );
        personResponse.surname( person.getSurname() );
        personResponse.age( person.getAge() );

        return personResponse.build();
    }
}
