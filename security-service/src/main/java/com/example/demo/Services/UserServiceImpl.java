package com.example.demo.Services;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.UserEntity;
import com.example.demo.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<UserEntity> getById(Long id) throws IllegalArgumentException{
		
		
		return userRepository
							.findById(id);
	}

	@Override
	public Optional<UserEntity> findByEmail(String email)throws IllegalArgumentException{ 
		// TODO Auto-generated method stub
		return userRepository
							.findByEmail(email);
				
	}

	@Override
	public UserEntity save(UserEntity userEntity)throws IllegalArgumentException, OptimisticLockingFailureException,NullPointerException  {
		// TODO Auto-generated method stub
		return userRepository
							.save(userEntity);
	}

	@Override
	public List<UserEntity> findAllById(List<Long> ids) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		return userRepository
							.findAllById(ids);
	}

	@Override
	public Long deleteById(Long id) {
		// TODO Auto-generated method stub
		userRepository
					.deleteById(id);  //id cancellato o non esistente
		return id;
	}

	@Override
	public String deleteByEmail(String email) {
		// TODO Auto-generated method stub
		userRepository
					.deleteByEmail(email);
		return email;
	}
	
	@Override
	public UserEntity updateUserById(UserEntity userEntity) {
		return null;
	}


	@Override
	public Optional<UserEntity> updateUserByEmail(UserEntity userEntity) {
		/*
		 * Can update:
		 * 		password;
		 * 		role;
		 * 		extraInfo
		 * 
		 */
		//Controllo che i valori di cui voglio fare l'eventuale update non siano nulli e poi li imposto sull'userInMemory
		Consumer<UserEntity> updateEntityInMemory=userInMemoryIn->
		{
			if(userEntity.getPassword()!="")userInMemoryIn.setPassword(userEntity.getPassword());
			if(userEntity.getRole()!="")userInMemoryIn.setRole(userEntity.getRole());
			if(userEntity.getExtraInfo()!="")userInMemoryIn.setExtraInfo(userEntity.getExtraInfo());
			userRepository.save(userInMemoryIn);
		};
		
			Optional<UserEntity>  userInMemoryOut
											=userRepository
														.findByEmail(userEntity.getEmail());
			userInMemoryOut
						.ifPresent(updateEntityInMemory);
			
		return userInMemoryOut;
		
	}

}
