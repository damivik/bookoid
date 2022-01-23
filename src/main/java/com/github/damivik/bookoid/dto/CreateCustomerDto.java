package com.github.damivik.bookoid.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.github.damivik.bookoid.validation.UniqueEmail;

public class CreateCustomerDto {
	
	@NotBlank
	@Size(max = 255)
	@Email
	@UniqueEmail
	private String email;
	
	@NotBlank
	@Size(max = 255)
	private String firstName;
	
	@NotBlank
	@Size(max = 255)
	private String lastName;
	
	@NotBlank
	@Size(min = 8, max = 30)
	private String password;
	
	public CreateCustomerDto() {
	}
	
	public CreateCustomerDto(String email, String firstName, String lastName, String password) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
