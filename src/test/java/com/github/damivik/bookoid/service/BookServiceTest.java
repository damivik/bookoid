package com.github.damivik.bookoid.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.github.damivik.bookoid.dto.CreateBookDto;
import com.github.damivik.bookoid.dto.UpdateBookDto;
import com.github.damivik.bookoid.entity.Book;
import com.github.damivik.bookoid.entity.Category;
import com.github.damivik.bookoid.repository.BookRepository;
import com.github.damivik.bookoid.repository.CategoryRepository;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private CategoryRepository categoryRepository;
	
	@Test
	void create() {
		String title = "1984";
		String authors = "Geroge Orwell";
		BigDecimal price = new BigDecimal("5500.00");
		Integer quantity = 100;
		Category classics = new Category("classics");
		long classicsId = 1;
		Category fiction = new Category("fiction");
		long fictionId = 2;
		List<Long> categoryIds = Arrays.asList(classicsId, fictionId);
		String description = "Nineteen Eighty-Four is a rare work that grows more haunting as its futuristic "
				+ "purgatory becomes more real...";
		CreateBookDto dto = new CreateBookDto(title, authors, price, quantity, categoryIds, description);
		Book expectedBook = new Book();
		Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(expectedBook);
		Mockito.when(categoryRepository.findById(classicsId)).thenReturn(Optional.of(classics));
		Mockito.when(categoryRepository.findById(fictionId)).thenReturn(Optional.of(fiction));
		BookService bookService = new BookService(bookRepository, categoryRepository);
		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		
		Book actualBook = bookService.create(dto);
		
		Mockito.verify(bookRepository).save(captor.capture());
		assertSame(expectedBook, actualBook);
		assertAll(
	            () -> assertEquals(title, captor.getValue().getTitle()),
	            () -> assertEquals(authors, captor.getValue().getAuthors()),
	            () -> assertEquals(price, captor.getValue().getPrice()),
	            () -> assertEquals(quantity, captor.getValue().getQuantity()),
	            () -> assertEquals(description, captor.getValue().getDescription()),
	            () -> assertTrue(captor.getValue().getCategories().contains(classics)),
	            () -> assertTrue(captor.getValue().getCategories().contains(fiction))
	    );
	}
	
	@Test
	void retrieve() throws Exception {
		Book expectedBook = new Book();
		long id = 1;
		Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(expectedBook));
		BookService bookService = new BookService(bookRepository, categoryRepository);
		
		Book actualBook = bookService.retrieve(id);
		
		assertSame(expectedBook, actualBook);
	}
	
	@Test
	void update() throws Exception {
		String title = "1984";
		String authors = "Geroge Orwell";
		BigDecimal price = new BigDecimal("5500.00");
		Integer quantity = 100;
		String description = "Nineteen Eighty-Four is a rare work that grows more haunting as its futuristic "
				+ "purgatory becomes more real...";
		long id = 1;
		UpdateBookDto dto = new UpdateBookDto(title, authors, price, quantity, description);
		Book book = new Book();
		Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
		Mockito.when(bookRepository.save(book)).thenReturn(book);
		BookService bookService = new BookService(bookRepository, categoryRepository);
		
		bookService.update(id, dto);
		
		assertAll(
	            () -> assertEquals(title, book.getTitle()),
	            () -> assertEquals(authors, book.getAuthors()),
	            () -> assertEquals(price, book.getPrice()),
	            () -> assertEquals(quantity, book.getQuantity()),
	            () -> assertEquals(description, book.getDescription())
	    );
	}
	
	@Test
	void delete() throws Exception {
		long id = 1;
		Book book = new Book();
		Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
		BookService bookService = new BookService(bookRepository, categoryRepository);
		
		bookService.delete(id);
		
		Mockito.verify(bookRepository).delete(book);
	}

}
