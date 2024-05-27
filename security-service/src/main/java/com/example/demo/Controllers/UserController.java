package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entities.UserEntity;
import com.example.demo.Services.UserService;
import com.example.demo.model.UserRequest;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	FromUserRequestToUserEntityImpl fromUserRequestToUserEntityImpl;
	
	@PostMapping("/new")
	public ResponseEntity<Long> createUser(@RequestBody UserRequest user) {
		
		try {
		UserEntity userSaved=userService.save(fromUserRequestToUserEntityImpl.perform(user));
		return ResponseEntity.ok(userSaved.getUserId());

		}catch(Exception e)
		{
			e.printStackTrace();
			return  ResponseEntity.badRequest().body(null);
		}
		
	}
	
	@GetMapping("/get")
	public ResponseEntity<UserEntity> get(@RequestBody UserRequest userRequest){
		UserEntity user=userService.findByEmail(userRequest.getEmail()).get();
		PasswordEncoder codec=new BCryptPasswordEncoder();
		
		if(codec.matches(userRequest.getPassword(), user.getPassword()))
		{
		return ResponseEntity.ok(user);
		}else
			return  ResponseEntity.badRequest().body(null);
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Long> delete(@RequestParam("id") Long id)
	{
		try {
			Long deletedId=userService.deleteById(id);
			return ResponseEntity.ok(deletedId);

		}catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return  ResponseEntity.badRequest().body(null);


	}
	




}
