package com.github.damivik.bookoid.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.damivik.bookoid.dto.CreateBookDto;
import com.github.damivik.bookoid.dto.UpdateBookDto;
import com.github.damivik.bookoid.dto.ViewBookDto;
import com.github.damivik.bookoid.entity.Book;
import com.github.damivik.bookoid.exception.BookNotFoundException;
import com.github.damivik.bookoid.service.BookService;
import com.github.damivik.bookoid.util.Mapper;

@RestController
public class BookController {
	private BookService bookService;
	private Mapper mapper;

	public BookController(BookService bookService, Mapper mapper) {
		this.bookService = bookService;
		this.mapper = mapper;
	}

	@PostMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ViewBookDto> create(@Valid @RequestBody CreateBookDto createBookDto) {
		Book book = bookService.create(createBookDto);
		URI location = URI.create("/books/" + book.getId());
		return ResponseEntity.created(location).body(mapper.mapBookToViewBookDto(book));
	}

	@GetMapping(path = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ViewBookDto retrieve(@PathVariable long id) {
		try {
			Book book = bookService.retrieve(id);
			return mapper.mapBookToViewBookDto(book);
		} catch (BookNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping(path = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ViewBookDto update(@PathVariable long id, @Valid @RequestBody UpdateBookDto dto) {
		try {
			Book book = bookService.update(id, dto);
			return mapper.mapBookToViewBookDto(book);
		} catch (BookNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/books/{id}")
	public void delete(@PathVariable long id) {
		try {
			bookService.delete(id);
		} catch (BookNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
