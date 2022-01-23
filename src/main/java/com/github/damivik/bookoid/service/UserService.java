package com.github.damivik.bookoid.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.damivik.bookoid.dto.CreateCustomerDto;
import com.github.damivik.bookoid.dto.UpdateCustomerDto;
import com.github.damivik.bookoid.entity.User;
import com.github.damivik.bookoid.exception.CustomerNotFoundException;
import com.github.damivik.bookoid.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createCustomer(CreateCustomerDto dto) {
		User user = new User(dto.getEmail(), dto.getFirstName(), dto.getLastName(), dto.getPassword(), true);
		return userRepository.save(user);
	}
	
	public User retrieveCustomer(long id) throws CustomerNotFoundException {
		return fetchCustomer(id);
	}
	
	public User updateCustomer(long id, UpdateCustomerDto dto) throws CustomerNotFoundException {
		User customer = fetchCustomer(id);
		
		if (dto.getFirstName() != null) {
			customer.setFirstName(dto.getFirstName());
		}
		if (dto.getLastName() != null) {
			customer.setLastName(dto.getLastName());
		}
		if (dto.getPassword() != null) {
			customer.setPassword(dto.getPassword());
		}
		
		return userRepository.save(customer);
	}
	
	public void deleteCustomer(long id) throws CustomerNotFoundException {
		User customer = fetchCustomer(id);
		userRepository.delete(customer);
	}
	
	private User fetchCustomer(long id) throws CustomerNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new CustomerNotFoundException();
		}
		
		return optionalUser.get();
	}
}
