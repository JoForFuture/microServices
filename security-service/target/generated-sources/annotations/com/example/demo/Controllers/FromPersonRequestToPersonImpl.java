package com.example.demo.Controllers;

import com.example.demo.Entities.Person;
import com.example.demo.model.PersonRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-06T09:10:58+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class FromPersonRequestToPersonImpl implements FromPersonRequestToPerson {

    @Override
    public Person perform(PersonRequest personRequest) {
        if ( personRequest == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.id( personRequest.getId() );
        person.name( personRequest.getName() );
        person.surname( personRequest.getSurname() );
        person.age( personRequest.getAge() );

        return person.build();
    }
}
