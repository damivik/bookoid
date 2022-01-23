package com.github.damivik.bookoid.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.github.damivik.bookoid.dto.CreateOrderDetailDto;
import com.github.damivik.bookoid.entity.Book;
import com.github.damivik.bookoid.entity.Order;
import com.github.damivik.bookoid.entity.OrderDetail;
import com.github.damivik.bookoid.repository.BookRepository;
import com.github.damivik.bookoid.repository.OrderDetailRepository;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class OrderDetailServiceTest {
	
	@Mock
	private OrderDetailRepository orderDetailRepository;
	
	@Mock
	private BookRepository bookRepository;
	
	@Test
	void create() {
		long bookId = 1;
		int orderQuantity = 5;
		int bookQuantity = 100;
		int expectedBookQuantityAfterOrderDetailIsCreated = 95;
		BigDecimal bookPrice = new BigDecimal(5000.00);
		BigDecimal expectedOrderPrice = new BigDecimal(25000);
		Book book = new Book();
		book.setQuantity(bookQuantity);
		book.setPrice(bookPrice);
		Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		Order order = new Order();
		CreateOrderDetailDto dto = new CreateOrderDetailDto(bookId, orderQuantity);
		OrderDetailService service = new OrderDetailService(bookRepository); 
		
		OrderDetail actualOrderDetail = service.create(order, dto);
		
		assertEquals(expectedBookQuantityAfterOrderDetailIsCreated, book.getQuantity());
		Mockito.verify(bookRepository).save(book);
		assertSame(order, actualOrderDetail.getOrder());
		assertSame(book, actualOrderDetail.getBook());
		assertEquals(orderQuantity, actualOrderDetail.getQuantity());
		assertEquals(expectedOrderPrice.doubleValue(), actualOrderDetail.getPrice().doubleValue());
	}

}
