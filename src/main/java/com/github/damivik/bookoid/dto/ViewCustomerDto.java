package com.github.damivik.bookoid.dto;

public class ViewCustomerDto {
	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	
	public ViewCustomerDto() {	
	}
	
	public ViewCustomerDto(Long id, String email, String firstName, String lastName) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
}