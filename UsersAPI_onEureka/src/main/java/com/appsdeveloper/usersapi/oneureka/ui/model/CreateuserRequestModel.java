package com.appsdeveloper.usersapi.oneureka.ui.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateuserRequestModel {
	
	@NotNull(message="first name can't be null")
	@Size(min=2,message="first name can't be less than two characters")
	private String firstName;
	
	@NotNull(message="first name can't be null")
	@Size(min=2,message="first name can't be less than two characters")
	private String lastName;
	
	private String email;
	
	private String password;
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
}
