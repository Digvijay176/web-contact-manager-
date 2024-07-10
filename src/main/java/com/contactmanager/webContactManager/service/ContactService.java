package com.contactmanager.webContactManager.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactmanager.webContactManager.entities.Contact;
import com.contactmanager.webContactManager.repository.ContactRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class ContactService {
	
	@Autowired
	private ContactRepository contactRepo;
	
	
	//add contact 
	public Contact addContact(Contact contact) {
		return contactRepo.save(contact);
	}
	
	//get contact by id
	public Optional<Contact> getContactById(int id){
		return contactRepo.findById(id);
	}
	
	//delete contact by id
	@Transactional
	public void deleteContactById(int id) {
		contactRepo.deleteById(id);;
	}
	
	//update contact
	public Contact updateContact(Contact contact, int id) {
		
		Contact dbContact =contactRepo.findById(id).get();
		
		dbContact.setName(contact.getName());
		dbContact.setEmail(contact.getEmail());
		dbContact.setContactNo(contact.getContactNo());
		dbContact.setDescription(contact.getDescription());
		dbContact.setWork(contact.getWork());
		
		return contactRepo.save(dbContact);
	}
}
