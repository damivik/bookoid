package com.github.damivik.bookoid.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.github.damivik.bookoid.dto.CreateCustomerDto;
import com.github.damivik.bookoid.dto.UpdateCustomerDto;
import com.github.damivik.bookoid.entity.User;
import com.github.damivik.bookoid.repository.UserRepository;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;

	@Test
	void createCustomer() {
		String email = "dami@mail.com";
		String firstName = "Dami";
		String lastName = "Vik";
		String password = "password";
		CreateCustomerDto dto = new CreateCustomerDto(email, firstName, lastName, password);
		User expectedUser = new User();
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(expectedUser);;
		UserService userService = new UserService(userRepository);
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		
		User actualUser = userService.createCustomer(dto);
		
		Mockito.verify(userRepository).save(captor.capture());
		assertSame(expectedUser, actualUser);
		assertAll(
	            () -> assertEquals(email, captor.getValue().getEmail()),
	            () -> assertEquals(firstName, captor.getValue().getFirstName()),
	            () -> assertEquals(lastName, captor.getValue().getLastName()),
	            () -> assertEquals(password, captor.getValue().getPassword()),
	            () -> assertEquals(true, captor.getValue().getIsCustomer())
	    );
	}
	
	@Test
	void retrieveCustomer() throws Exception {
		User expectedUser = new User();
		long id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));
		UserService userService = new UserService(userRepository);
		
		User actualUser = userService.retrieveCustomer(id);
		
		assertSame(expectedUser, actualUser);
	}
	
	@Test
	void updateCustomer() throws Exception {
		String firstName = "Dami";
		String lastName = "Vik";
		String password = "password";
		long id = 1;
		UpdateCustomerDto dto = new UpdateCustomerDto(firstName, lastName, password);
		User customer = new User();
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(customer));
		Mockito.when(userRepository.save(customer)).thenReturn(customer);;
		UserService userService = new UserService(userRepository);
		
		userService.updateCustomer(id, dto);
		
		assertAll(
	            () -> assertEquals(firstName, customer.getFirstName()),
	            () -> assertEquals(lastName, customer.getLastName()),
	            () -> assertEquals(password, customer.getPassword())
	    );
	}
	
	@Test
	void deleteCustomer() throws Exception {
		long id = 1;
		User customer = new User();
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(customer));
		UserService userService = new UserService(userRepository);
		
		userService.deleteCustomer(id);
		
		Mockito.verify(userRepository).delete(customer);
	}
}
