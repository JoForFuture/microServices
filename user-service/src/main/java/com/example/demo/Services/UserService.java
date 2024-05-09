package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.UserEntity;

@Service
public interface UserService {
	
	public Optional<UserEntity> getById(Long id);
	
	public Optional<UserEntity> findByEmail(String email);
	
	public UserEntity save(UserEntity userEntity);
	
	public List<UserEntity> findAllById(List<Long> ids);
	
	public Long deleteById(Long id);
	
	public String deleteByEmail(String email);
	
	public UserEntity updateUserById(UserEntity userEntity);
	
	public Optional<UserEntity> updateUserByEmail(UserEntity userEntity);
	
	
	
	
	
	

}
