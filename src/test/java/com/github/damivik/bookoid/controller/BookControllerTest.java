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

import com.github.damivik.bookoid.dto.CreateBookDto;
import com.github.damivik.bookoid.dto.UpdateBookDto;
import com.github.damivik.bookoid.dto.ViewBookDto;
import com.github.damivik.bookoid.entity.Book;
import com.github.damivik.bookoid.service.BookService;
import com.github.damivik.bookoid.util.Mapper;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class BookControllerTest {

	@Mock
	private BookService bookService;
	
	@Mock
	private Mapper mapper;
	
	@Test
	void create() throws Exception {
		long id = 1;
		Book book = new Book();
		book.setId(id);
		URI location = URI.create("/books/" + id);
		CreateBookDto createDto = new CreateBookDto();
		ViewBookDto viewDto = new ViewBookDto();
		Mockito.when(bookService.create(createDto)).thenReturn(book);
		Mockito.when(mapper.mapBookToViewBookDto(book)).thenReturn(viewDto);
		BookController controller = new BookController(bookService, mapper);
		
		ResponseEntity<ViewBookDto> responseEntity = controller.create(createDto);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(location, responseEntity.getHeaders().getLocation());
		assertEquals(viewDto, responseEntity.getBody());
	}

	@Test
	void retrieve() throws Exception {
		long id = 1;
		Book book = new Book();
		ViewBookDto expectedViewDto = new ViewBookDto();
		Mockito.when(bookService.retrieve(id)).thenReturn(book);
		Mockito.when(mapper.mapBookToViewBookDto(book)).thenReturn(expectedViewDto);
		BookController controller = new BookController(bookService, mapper);
		
		ViewBookDto actualViewDto = controller.retrieve(id);
		
		assertSame(expectedViewDto, actualViewDto);
	}
	
	@Test
	void update() throws Exception {
		long id = 1;
		Book book = new Book();
		ViewBookDto expectedViewDto = new ViewBookDto();
		UpdateBookDto updateDto = new UpdateBookDto();
		Mockito.when(bookService.update(id, updateDto)).thenReturn(book);
		Mockito.when(mapper.mapBookToViewBookDto(book)).thenReturn(expectedViewDto);
		BookController controller = new BookController(bookService, mapper);
		
		ViewBookDto actualViewDto = controller.update(id, updateDto);
		
		Mockito.verify(bookService).update(id, updateDto);
		assertSame(expectedViewDto, actualViewDto);
	}
	
	@Test
	void delete() throws Exception {
		long id = 1;
		BookController controller = new BookController(bookService, mapper);
		
		controller.delete(id);
		
		Mockito.verify(bookService).delete(id);
	}

}
