package com.contactmanager.webContactManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contactmanager.webContactManager.entities.Contact;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
	
	//find contact by user 
	@Query("from Contact as a where a.user.id=:userId")
	public Page<Contact> findContactByUser(@Param("userId")int userId,Pageable p);
		
}
