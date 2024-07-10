package com.contactmanager.webContactManager.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Size(min = 3,max = 20 , message = "min 3 and max 20 character are allowed !!")
	@Pattern(regexp = "[a-zA-Z]*", message = "Only accept the characters !!")
	private String userName;
	
	@Column(unique = true,nullable = true)
//	@UniqueElements(message = "plase try with another email or please login!!")
	@NotBlank(message = "please enter valid email!!")
	private String email;
	
	@Size(min = 8, message = "password should have min 8 char !!")
	private String password;
	@Size(max=10000, message= "max range should be 1000 character")
	private String description;
	private String role;
	private boolean active;
	private String imgUrl;
	
	@AssertTrue(message = "please check turms and conditions!!")
	private boolean turms;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user",orphanRemoval = true)
	@JsonManagedReference
	private List<Contact> contact = new ArrayList<Contact>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public List<Contact> getContact() {
		return contact;
	}
	
	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}
	
	public boolean getTurms() {
		return turms;
	}
	
	public void setTurms(boolean turms) {
		this.turms = turms;
	}
}
