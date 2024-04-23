package com.example.demo.Controllers;

import com.example.demo.Entities.Person;
import com.example.demo.model.PersonRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-23T08:29:09+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class FromPersonRequestToPersonImpl implements FromPersonRequestToPerson {

    @Override
    public Person perform(PersonRequest personRequest) {
        if ( personRequest == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.age( personRequest.getAge() );
        person.id( personRequest.getId() );
        person.name( personRequest.getName() );
        person.surname( personRequest.getSurname() );

        return person.build();
    }
}
