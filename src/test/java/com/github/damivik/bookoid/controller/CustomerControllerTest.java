package com.github.damivik.bookoid.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.damivik.bookoid.dto.CreateCustomerDto;
import com.github.damivik.bookoid.dto.UpdateCustomerDto;
import com.github.damivik.bookoid.dto.ViewCustomerDto;
import com.github.damivik.bookoid.entity.User;
import com.github.damivik.bookoid.service.UserService;
import com.github.damivik.bookoid.util.Mapper;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class CustomerControllerTest {

	@Mock
	private UserService userService;
	
	@Mock
	private Mapper mapper;
	
	@Test
	void create() {
		long id = 1;
		User customer = new User();
		customer.setId(id);
		URI location = URI.create("/customers/" + id);
		CreateCustomerDto createDto = new CreateCustomerDto();
		ViewCustomerDto viewDto = new ViewCustomerDto();
		Mockito.when(userService.createCustomer(createDto)).thenReturn(customer);
		Mockito.when(mapper.mapUserToViewCustomerDto(customer)).thenReturn(viewDto);
		CustomerController controller = new CustomerController(userService, mapper);
		
		ResponseEntity<ViewCustomerDto> responseEntity = controller.create(createDto);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(location, responseEntity.getHeaders().getLocation());
		assertEquals(viewDto, responseEntity.getBody());
	}
	
	@Test
	void retrieve() throws Exception {
		long id = 1;
		User customer = new User();
		ViewCustomerDto expectedViewDto = new ViewCustomerDto();
		Mockito.when(userService.retrieveCustomer(id)).thenReturn(customer);
		Mockito.when(mapper.mapUserToViewCustomerDto(customer)).thenReturn(expectedViewDto);
		CustomerController controller = new CustomerController(userService, mapper);
		
		ViewCustomerDto actualViewDto = controller.retrieve(id);
		
		assertSame(expectedViewDto, actualViewDto);
	}
	
	@Test
	void update() throws Exception {
		long id = 1;
		User customer = new User();
		ViewCustomerDto expectedViewDto = new ViewCustomerDto();
		UpdateCustomerDto updateDto = new UpdateCustomerDto();
		Mockito.when(userService.updateCustomer(id, updateDto)).thenReturn(customer);
		Mockito.when(mapper.mapUserToViewCustomerDto(customer)).thenReturn(expectedViewDto);
		CustomerController controller = new CustomerController(userService, mapper);
		
		ViewCustomerDto actualViewDto = controller.update(id, updateDto);
		
		Mockito.verify(userService).updateCustomer(id, updateDto);
		assertSame(expectedViewDto, actualViewDto);
	}
	
	@Test
	void delete() throws Exception {
		long id = 1;
		CustomerController controller = new CustomerController(userService, mapper);
		
		controller.delete(id);
		
		Mockito.verify(userService).deleteCustomer(id);
	}

}
