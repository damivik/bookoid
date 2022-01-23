package com.github.damivik.bookoid.endpointtest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damivik.bookoid.dto.CreateOrderDetailDto;
import com.github.damivik.bookoid.dto.CreateOrderDto;
import com.github.damivik.bookoid.entity.Book;
import com.github.damivik.bookoid.entity.Order;
import com.github.damivik.bookoid.entity.User;

public class OrdersTest extends Base {

	@Test
	public void create() throws Exception {
		List<Book> books = createBooks();
		User customer = createCustomer();
		CreateOrderDetailDto createOrderDetailDto1 = new CreateOrderDetailDto(books.get(0).getId(), 3);
		CreateOrderDetailDto createOrderDetailDto2 = new CreateOrderDetailDto(books.get(1).getId(), 4);
		CreateOrderDto createOrderDto = new CreateOrderDto(customer.getId(),
				Arrays.asList(createOrderDetailDto1, createOrderDetailDto2));
		String payload = new ObjectMapper().writeValueAsString(createOrderDto);
		
		mvc
			.perform(
					post("/orders")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(payload))
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"))
			.andExpect(jsonPath("$.customer.email", is(customer.getEmail())))
			.andExpect(jsonPath("$.customer.firstName", is(customer.getFirstName())))
			.andExpect(jsonPath("$.customer.lastName", is(customer.getLastName())))
			.andExpect(jsonPath("$.items.[0].book", is("/books/" + createOrderDetailDto1.getBookId())))
			.andExpect(jsonPath("$.items.[0].quantity", is(createOrderDetailDto1.getQuantity())))
			.andExpect(jsonPath("$.items.[1].book", is("/books/" + createOrderDetailDto2.getBookId())))
			.andExpect(jsonPath("$.items.[1].quantity", is(createOrderDetailDto2.getQuantity())));
	}
	
	@Test
	public void retrieve() throws Exception {
		Order order = createOrder();
		
		mvc
			.perform(get("/orders/" + order.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(order.getId().intValue())))
			.andExpect(jsonPath("$.customer.email", is(order.getCustomer().getEmail())))
			.andExpect(jsonPath("$.customer.firstName", is(order.getCustomer().getFirstName())))
			.andExpect(jsonPath("$.customer.lastName", is(order.getCustomer().getLastName())))
			.andExpect(jsonPath("$.items.[0].book", is("/books/" + order.getOrderDetails().get(0).getBook().getId().intValue())))
			.andExpect(jsonPath("$.items.[0].quantity", is(order.getOrderDetails().get(0).getQuantity())))
			.andExpect(jsonPath("$.items.[1].book", is("/books/" + order.getOrderDetails().get(1).getBook().getId().intValue())))
			.andExpect(jsonPath("$.items.[1].quantity", is(order.getOrderDetails().get(1).getQuantity())));
	}
	
	@AfterEach
	public void tearDown() {
		refreshDatabse();
	}
	
}
