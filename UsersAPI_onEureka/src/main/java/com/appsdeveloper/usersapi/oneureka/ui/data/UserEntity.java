package com.appsdeveloper.usersapi.oneureka.ui.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenerationTime;


@Entity
@Table(name="users")   //will create a table if it's not existing already
public class UserEntity implements Serializable {


	private static final long serialVersionUID = -7410520667771520662L;

	@Column(nullable = false, length =50)
	private String firstName;
	
	@Column(nullable = false, length =50)
	private String lastName;
	
	@Column(nullable = false, length =120, unique = true)  //unique for single value of email - no repeatation 
	private String email;
	
	
	private String password;
	
	@Column(nullable = false, unique = true)
	private String userId;
	
	@Column(nullable = false, unique = true)
	private String encryptedPassword;
	
	// this is for the db id
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String dbID;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getDbID() {
		return dbID;
	}

	public void setDbID(String dbID) {
		this.dbID = dbID;
	}

}
