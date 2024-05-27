package com.example.demo.Controllers;

import com.example.demo.Entities.UserEntity;
import com.example.demo.model.UserRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-24T08:01:22+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 22.0.1 (Eclipse Adoptium)"
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
        userEntity.extraInfo( userRequest.getExtraInfo() );
        userEntity.password( userRequest.getPassword() );
        userEntity.role( userRequest.getRole() );

        return userEntity.build();
    }
}
