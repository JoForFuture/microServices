package com.example.demo.Controllers;

import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-28T09:21:50+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class FromPersonResponseToPersonRequestImpl implements FromPersonResponseToPersonRequest {

    @Override
    public PersonRequest perform(PersonResponse personResponse) {
        if ( personResponse == null ) {
            return null;
        }

        PersonRequest.PersonRequestBuilder personRequest = PersonRequest.builder();

        personRequest.id( personResponse.getId() );
        personRequest.name( personResponse.getName() );
        personRequest.surname( personResponse.getSurname() );
        personRequest.age( personResponse.getAge() );

        return personRequest.build();
    }
}
