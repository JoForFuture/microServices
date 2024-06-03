package com.example.demo.Controllers;

import com.example.demo.Entities.UserEntity;
import com.example.demo.model.UserRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-28T09:21:50+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class FromUserRequestToUserEntityImpl implements FromUserRequestToUserEntity {

    @Override
    public UserEntity perform(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.email( userRequest.getEmail() );
        userEntity.password( userRequest.getPassword() );
        userEntity.role( userRequest.getRole() );
        userEntity.extraInfo( userRequest.getExtraInfo() );

        return userEntity.build();
    }
}
