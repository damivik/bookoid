package com.github.damivik.bookoid.dto;

import javax.validation.constraints.Size;

public class UpdateCustomerDto {
	
	@Size(max = 255)
	private String firstName;
	
	@Size(max = 255)
	private String lastName;
	
	@Size(min = 8, max = 30)
	private String password;

	public UpdateCustomerDto() {
	}
	
	public UpdateCustomerDto(String firstName, String lastName,	String password) {
		this.firstName = firstName;
		this.lastName = lastName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
