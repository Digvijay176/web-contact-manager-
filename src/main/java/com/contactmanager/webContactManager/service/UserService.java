package com.contactmanager.webContactManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.contactmanager.webContactManager.entities.CustomUserDetails;
import com.contactmanager.webContactManager.entities.User;
import com.contactmanager.webContactManager.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	//get user by id
	public Optional<User> getUserById(int id){
		return userRepo.findById(id);
	}
	
	//get all user
	public List<User>getAllUser(){
		return userRepo.findAll();
	}
	
	//add user
	public User addUser(User user) {
		return userRepo.save(user);
	}
	
	//delete user by id 
	public void deleteById(int id) {
		userRepo.deleteById(id);
	}
	
	//update user
	public User updateUser(User user, int id) {
		Optional<User> oldDetails = getUserById(id); 
		if(oldDetails.isEmpty())return null;
		
		User details = oldDetails.get();
		
		details.setUserName(user.getUserName());
		details.setEmail(user.getEmail());
		details.setContact(user.getContact());
		details.setDescription(user.getDescription());
		details.setPassword(user.getPassword());
		details.setImgUrl(user.getImgUrl());
		details.setRole(user.getRole());
		
		return userRepo.save(details);
	}
	
}
