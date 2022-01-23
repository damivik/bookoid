package com.github.damivik.bookoid.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.damivik.bookoid.dto.CreateCustomerDto;
import com.github.damivik.bookoid.dto.UpdateCustomerDto;
import com.github.damivik.bookoid.dto.ViewCustomerDto;
import com.github.damivik.bookoid.entity.User;
import com.github.damivik.bookoid.exception.CustomerNotFoundException;
import com.github.damivik.bookoid.service.UserService;
import com.github.damivik.bookoid.util.Mapper;

@RestController
public class CustomerController {
	private UserService userService;
	private Mapper mapper;

	public CustomerController(UserService userService, Mapper mapper) {
		this.userService = userService;
		this.mapper = mapper;
	}

	@PostMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ViewCustomerDto> create(@Valid CreateCustomerDto createCustomerDto) {
		User customer = userService.createCustomer(createCustomerDto);
		URI location = URI.create("/customers/" + customer.getId());
		return ResponseEntity.created(location).body(mapper.mapUserToViewCustomerDto(customer));
	}

	@GetMapping(path = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ViewCustomerDto retrieve(@PathVariable long id) {
		try {
			User customer = userService.retrieveCustomer(id);
			return mapper.mapUserToViewCustomerDto(customer);
		} catch (CustomerNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping(path = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ViewCustomerDto update(@PathVariable long id, @Valid UpdateCustomerDto dto) {
		try {
			User customer = userService.updateCustomer(id, dto);
			return mapper.mapUserToViewCustomerDto(customer);
		} catch (CustomerNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/customers/{id}")
	public void delete(@PathVariable long id) {
		try {
			userService.deleteCustomer(id);
		} catch (CustomerNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
