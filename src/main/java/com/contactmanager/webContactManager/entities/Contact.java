package com.contactmanager.webContactManager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	@Size(min = 3,max = 20, message = "name size should be between 3 to 20 !")
	private String name;
	private String nickName;
	@Column(unique = true)
	private String email;
	private String work;
	@Size(min=10,max=10,message = "please enter valid contact number !")
	private String contactNo;
	private String imageUrl;
	@Column(length = 200)
	private String description;
	
	@ManyToOne
	@JsonBackReference
	private User user;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Contact [cid=" + cid + ", name=" + name + ", nickName=" + nickName + ", email=" + email + ", work="
				+ work + ", contactNo=" + contactNo + ", imageUrl=" + imageUrl + ", description=" + description
				+ ", user=" + user + "]";
	}	
}
