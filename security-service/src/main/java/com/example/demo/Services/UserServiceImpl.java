package com.example.demo.Services;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.UserEntity;
import com.example.demo.Repositories.UserRepository;

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

		findByEmail(userEntity.getEmail()).ifPresentOrElse(email->{
			System.err.println(email+" already exist!");
			throw new IllegalArgumentException();
		}, ()->{
			PasswordEncoder codec=new BCryptPasswordEncoder();
			String encodedPassword =codec.encode(userEntity.getPassword());
			userEntity.setPassword(encodedPassword);
		});;
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
	public Long deleteById(Long id) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		getById(id).ifPresentOrElse(entity->{
			userRepository.deleteById(entity.getUserId()); 
		}, ()->{
			throw new IllegalArgumentException();
		});
		 
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
