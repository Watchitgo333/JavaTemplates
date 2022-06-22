package com.codingdojo.projectmanager.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginUser {

	@NotEmpty(message="Please enter a valid email.")
	@Email(message="Please enter a valid email.")
	private String email;
	
	@NotEmpty(message="Please enter a valid Password")
	private String password;
	
	public LoginUser() {}

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
