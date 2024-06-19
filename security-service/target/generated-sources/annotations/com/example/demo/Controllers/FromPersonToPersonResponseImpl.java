package com.example.demo.Controllers;

import com.example.demo.Entities.Person;
import com.example.demo.model.PersonResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T15:18:38+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 22.0.1 (Eclipse Adoptium)"
)
@Component
public class FromPersonToPersonResponseImpl implements FromPersonToPersonResponse {

    @Override
    public PersonResponse perform(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonResponse.PersonResponseBuilder personResponse = PersonResponse.builder();

        personResponse.age( person.getAge() );
        personResponse.id( person.getId() );
        personResponse.name( person.getName() );
        personResponse.surname( person.getSurname() );

        return personResponse.build();
    }
}
