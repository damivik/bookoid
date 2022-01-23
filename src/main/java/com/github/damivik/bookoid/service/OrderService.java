package com.github.damivik.bookoid.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.damivik.bookoid.dto.CreateOrderDetailDto;
import com.github.damivik.bookoid.dto.CreateOrderDto;
import com.github.damivik.bookoid.entity.Order;
import com.github.damivik.bookoid.entity.OrderDetail;
import com.github.damivik.bookoid.entity.User;
import com.github.damivik.bookoid.exception.OrderNotFoundException;
import com.github.damivik.bookoid.repository.OrderRepository;
import com.github.damivik.bookoid.repository.UserRepository;

@Service
public class OrderService {
	private OrderRepository orderRepository;
	private UserRepository userRepository;
	private OrderDetailService orderDetailService;

	public OrderService(OrderRepository orderRepository, UserRepository userRepository,
			OrderDetailService orderDetailService) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.orderDetailService = orderDetailService;
	}

	public Order place(CreateOrderDto createOrderDto) {
		User customer = userRepository.findById(createOrderDto.getCustomerId()).get();
		Order order = new Order(customer);
		BigDecimal totalPrice = new BigDecimal(0);
		orderRepository.save(order);
		for (CreateOrderDetailDto createOrderDetailDto : createOrderDto.getCreateOrderDetailDtos()) {
			OrderDetail orderDetail = orderDetailService.create(order, createOrderDetailDto);
			order.addOrderDetail(orderDetail);
			totalPrice = totalPrice.add(orderDetail.getPrice());
		}
		order.setTotalPrice(totalPrice);
		
		return orderRepository.save(order);
	}

	public Order retrieve(long id) throws OrderNotFoundException {
		Optional<Order> optionalOrder = orderRepository.findById(id);
		if (optionalOrder.isEmpty()) {
			throw new OrderNotFoundException();
		}
		return optionalOrder.get();
	}

}
