package com.example.demo.Controllers;

import com.example.demo.Entities.UserEntity;
import com.example.demo.model.UserRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-25T16:09:32+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240524-2033, environment: Java 22.0.1 (Eclipse Adoptium)"
)
@Component
public class FromUserRequestToUserEntityImpl implements FromUserRequestToUserEntity {

    @Override
    public UserEntity perform(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail( userRequest.getEmail() );
        userEntity.setExtraInfo( userRequest.getExtraInfo() );
        userEntity.setPassword( userRequest.getPassword() );
        userEntity.setRole( userRequest.getRole() );

        return userEntity;
    }
}
