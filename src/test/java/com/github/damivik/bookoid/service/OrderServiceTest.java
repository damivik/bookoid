package com.github.damivik.bookoid.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.github.damivik.bookoid.dto.CreateOrderDetailDto;
import com.github.damivik.bookoid.dto.CreateOrderDto;
import com.github.damivik.bookoid.entity.Order;
import com.github.damivik.bookoid.entity.OrderDetail;
import com.github.damivik.bookoid.entity.User;
import com.github.damivik.bookoid.repository.OrderRepository;
import com.github.damivik.bookoid.repository.UserRepository;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class OrderServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderDetailService orderDetailService;

	@Test
	void place() {
		long customerId = 5;
		CreateOrderDetailDto createOrderDetailDto1 = new CreateOrderDetailDto(1, 3);
		CreateOrderDetailDto createOrderDetailDto2 = new CreateOrderDetailDto(2, 4);
		CreateOrderDto createOrderDto = new CreateOrderDto(customerId,
				Arrays.asList(createOrderDetailDto1, createOrderDetailDto2));
		User customer = new User();
		Mockito.when(userRepository.findById(customerId)).thenReturn(Optional.of(customer));
		Order expectedOrder = new Order();
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(expectedOrder);
		OrderService orderService = new OrderService(orderRepository, userRepository, orderDetailService);
		OrderDetail orderDetail1 = new OrderDetail();
		orderDetail1.setPrice(new BigDecimal(200.05));
		OrderDetail orderDetail2 = new OrderDetail();
		orderDetail2.setPrice(new BigDecimal(200.05));
		double expectedTotalPrice = 400.10;
		Mockito.when(orderDetailService.create(expectedOrder, createOrderDetailDto1)).thenReturn(orderDetail1);
		Mockito.when(orderDetailService.create(expectedOrder, createOrderDetailDto2)).thenReturn(orderDetail2);
		ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
		
		orderService.place(createOrderDto); 
		
		Mockito.verify(orderRepository, Mockito.times(2)).save(captor.capture());
		assertSame(customer, captor.getValue().getCustomer());
		assertEquals(expectedTotalPrice, captor.getValue().getTotalPrice().doubleValue());
	}

}
