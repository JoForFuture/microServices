package com.example.demo.Controllers;

import org.mapstruct.Mapper;

import com.example.demo.Entities.UserEntity;
import com.example.demo.model.UserRequest;

@Mapper(componentModel="spring")
public interface FromUserRequestToUserEntity {

	UserEntity perform(UserRequest userRequest);
}
