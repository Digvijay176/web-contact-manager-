package com.contactmanager.webContactManager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import com.contactmanager.webContactManager.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public Optional<User> findByEmail(String email);
}
