package com.github.damivik.bookoid.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.github.damivik.bookoid.dto.CreateOrderDetailDto;
import com.github.damivik.bookoid.entity.Book;
import com.github.damivik.bookoid.entity.Order;
import com.github.damivik.bookoid.entity.OrderDetail;
import com.github.damivik.bookoid.repository.BookRepository;

@Service
public class OrderDetailService {
	private BookRepository bookRepository;

	public OrderDetail create(Order order, CreateOrderDetailDto dto) {
		Book book = bookRepository.findById(dto.getBookId()).get();
		book.setQuantity(book.getQuantity() - dto.getQuantity());
		bookRepository.save(book);
		BigDecimal price = book.getPrice().multiply(new BigDecimal(dto.getQuantity()));
		OrderDetail orderDetail = new OrderDetail(order, book, dto.getQuantity(), price);
	
		return orderDetail;
	}

	public OrderDetailService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

}
