package com.github.damivik.bookoid.endpointtest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.github.damivik.bookoid.entity.Book;
import com.github.damivik.bookoid.entity.Category;
import com.github.damivik.bookoid.entity.Order;
import com.github.damivik.bookoid.entity.OrderDetail;
import com.github.damivik.bookoid.entity.User;
import com.github.damivik.bookoid.repository.BookRepository;
import com.github.damivik.bookoid.repository.CategoryRepository;
import com.github.damivik.bookoid.repository.OrderDetailRepository;
import com.github.damivik.bookoid.repository.OrderRepository;
import com.github.damivik.bookoid.repository.UserRepository;

@Tag("integration-test")
@SpringBootTest
@AutoConfigureMockMvc
public class Base {

	@Autowired
	protected MockMvc mvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	protected User createCustomer() {
		String email = "dami@mail.com";
		String firstName = "Dami";
		String lastName = "Vik";
		String password = "password";

		return userRepository.save(new User(email, firstName, lastName, password, true));
	}

	protected Category createCategory() {
		return categoryRepository.save(new Category("Non Fiction"));
	}

	protected Category createCategory(String name) {
		return categoryRepository.save(new Category(name));
	}

	protected Book createBook() {
		String title = "1984";
		String authors = "Geroge Orwell";
		BigDecimal price = new BigDecimal("5500.00");
		int quantity = 100;
		List<Category> categories = Arrays.asList(createCategory("Classics"), createCategory("Fiction"));
		String description = "Nineteen Eighty-Four is a rare work that grows more haunting as its futuristic "
				+ "purgatory becomes more real...";
		
		Book book = new Book(title, authors, price, quantity, description);
		book.setCategories(categories);
		
		return bookRepository.save(book);
	}
	
	protected List<Book> createBooks() {
		Book book1 = createBook();
		String title = "To Kill a Mockingbird";
		String authors = "Harper Lee";
		BigDecimal price = new BigDecimal("5500.00");
		int quantity = 100;
		List<Category> categories = Arrays.asList(createCategory("Classics"), createCategory("Fiction"));
		String description = "The unforgettable novel of a childhood in a sleepy Southern town and the crisis of "
				+ "conscience that rocked it...";
		Book book2 = new Book(title, authors, price, quantity, description);
		book2.setCategories(categories);
		book2 = bookRepository.save(book2);
		
		return Arrays.asList(book1, book2);
	}
	
	protected Order createOrder() {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setBook(createBook());
		orderDetail.setPrice(new BigDecimal(300.15));
		orderDetail.setQuantity(3);
		
		OrderDetail orderDetail2 = new OrderDetail();
		orderDetail2.setBook(createBook());
		orderDetail2.setPrice(new BigDecimal(800.00));
		orderDetail2.setQuantity(4);
		
		Order order = new Order();
		order.setCustomer(createCustomer());
		order.setTotalPrice(new BigDecimal(1100.15));
		order.addOrderDetail(orderDetail);
		order.addOrderDetail(orderDetail2);
		
		return orderRepository.save(order);
	}

	protected void refreshDatabse() {
		orderDetailRepository.deleteAll();
		orderRepository.deleteAll();
		userRepository.deleteAll();
		bookRepository.deleteAll();
		categoryRepository.deleteAll();
	}

}
