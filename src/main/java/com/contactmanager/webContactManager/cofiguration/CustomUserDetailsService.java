package com.contactmanager.webContactManager.cofiguration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.contactmanager.webContactManager.entities.User;
import com.contactmanager.webContactManager.repository.UserRepository;


public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userOpt = userRepository.findByEmail(username);
		
		if(userOpt.isEmpty()) throw new UsernameNotFoundException(username);
		else {
			User user = userOpt.get();
			UserDetails userDetails = org.springframework.security.core.userdetails.User
									  .builder()
									  .username(user.getEmail())
									  .password(user.getPassword())
									  .roles(user.getRole())
									  .build();
			
			return userDetails;
		}
	}

}
