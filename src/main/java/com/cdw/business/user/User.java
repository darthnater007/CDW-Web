package com.cdw.business.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String bio;
	private String email;
	private String phone;
	@Column (name = "IsAdmin")
	private boolean admin;

	
	
	public User() {
		
	}



	public User(String userName, String password, String firstName, String lastName, String bio, String email,
			String phone, boolean admin) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bio = bio;
		this.email = email;
		this.phone = phone;
		this.admin = admin;
	}



	public User(int id, String userName, String password, String firstName, String lastName, String bio, String email,
			String phone, boolean admin) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bio = bio;
		this.email = email;
		this.phone = phone;
		this.admin = admin;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", admin="
				+ admin + "]";
	}



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



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getBio() {
		return bio;
	}



	public void setBio(String bio) {
		this.bio = bio;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public boolean isAdmin() {
		return admin;
	}



	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	
}
