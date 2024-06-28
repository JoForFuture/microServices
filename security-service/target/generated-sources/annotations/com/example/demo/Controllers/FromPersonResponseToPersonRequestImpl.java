package com.example.demo.Controllers;

import com.example.demo.model.PersonRequest;
import com.example.demo.model.PersonResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-25T16:09:32+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240524-2033, environment: Java 22.0.1 (Eclipse Adoptium)"
)
@Component
public class FromPersonResponseToPersonRequestImpl implements FromPersonResponseToPersonRequest {

    @Override
    public PersonRequest perform(PersonResponse personResponse) {
        if ( personResponse == null ) {
            return null;
        }

        PersonRequest.PersonRequestBuilder personRequest = PersonRequest.builder();

        personRequest.age( personResponse.getAge() );
        personRequest.id( personResponse.getId() );
        personRequest.name( personResponse.getName() );
        personRequest.surname( personResponse.getSurname() );

        return personRequest.build();
    }
}
