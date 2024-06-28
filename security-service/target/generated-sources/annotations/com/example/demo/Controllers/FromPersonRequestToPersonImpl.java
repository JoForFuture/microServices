package com.example.demo.Controllers;

import com.example.demo.Entities.Person;
import com.example.demo.model.PersonRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-25T16:09:32+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240524-2033, environment: Java 22.0.1 (Eclipse Adoptium)"
)
@Component
public class FromPersonRequestToPersonImpl implements FromPersonRequestToPerson {

    @Override
    public Person perform(PersonRequest personRequest) {
        if ( personRequest == null ) {
            return null;
        }

        Person person = new Person();

        person.setId( personRequest.getId() );
        person.setName( personRequest.getName() );
        person.setSurname( personRequest.getSurname() );
        person.setAge( personRequest.getAge() );

        return person;
    }
}
