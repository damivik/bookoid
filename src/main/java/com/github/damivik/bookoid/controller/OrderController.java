package com.github.damivik.bookoid.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.damivik.bookoid.dto.CreateOrderDto;
import com.github.damivik.bookoid.dto.ViewOrderDto;
import com.github.damivik.bookoid.entity.Order;
import com.github.damivik.bookoid.exception.OrderNotFoundException;
import com.github.damivik.bookoid.service.OrderService;
import com.github.damivik.bookoid.util.Mapper;

@RestController
public class OrderController {
	private OrderService orderService;
	private Mapper mapper;
	
	public OrderController(OrderService orderService, Mapper mapper) {
		this.orderService = orderService;
		this.mapper = mapper;
	}

	@PostMapping(path = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ViewOrderDto> create(@Valid @RequestBody CreateOrderDto dto) {
		Order order = orderService.place(dto);
		URI location = URI.create("/books/" + order.getId());
		return ResponseEntity.created(location).body(mapper.mapOrderToViewOrderDto(order));
	}

	@GetMapping(path = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ViewOrderDto retrieve(@PathVariable long id) {
		try {
			Order order = orderService.retrieve(id);
			return mapper.mapOrderToViewOrderDto(order);
		} catch (OrderNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
}
