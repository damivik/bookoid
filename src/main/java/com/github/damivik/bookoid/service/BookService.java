package com.github.damivik.bookoid.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.damivik.bookoid.dto.CreateBookDto;
import com.github.damivik.bookoid.dto.UpdateBookDto;
import com.github.damivik.bookoid.entity.Book;
import com.github.damivik.bookoid.entity.Category;
import com.github.damivik.bookoid.exception.BookNotFoundException;
import com.github.damivik.bookoid.repository.BookRepository;
import com.github.damivik.bookoid.repository.CategoryRepository;

@Service
public class BookService {
	private BookRepository bookRepository;
	private CategoryRepository categoryRepository;

	public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
		this.bookRepository = bookRepository;
		this.categoryRepository = categoryRepository;
	}

	public Book create(CreateBookDto dto) {
		Book book = new Book(dto.getTitle(), dto.getAuthors(), dto.getPrice(), dto.getQuantity(), dto.getDescription());
		
		List<Category> categories = new ArrayList<>();
		for (long categoryId : dto.getCategoryIds()) {
			Category category = categoryRepository.findById(categoryId).get();
			categories.add(category);
		}
		if (categories.size() > 0) {
			book.setCategories(categories);
		}

		return bookRepository.save(book);
	}
	
	public Book retrieve(long id) throws BookNotFoundException {
		return fetch(id);
	}
	
	public Book update(long id, UpdateBookDto dto) throws BookNotFoundException {
		Book book = fetch(id);
		
		if (dto.getTitle() != null) {
			book.setTitle(dto.getTitle());
		}
		if (dto.getAuthors() != null) {
			book.setAuthors(dto.getAuthors());
		}
		if (dto.getPrice() != null) {
			book.setPrice(dto.getPrice());
		}
		if (dto.getQuantity() != null) {
			book.setQuantity(dto.getQuantity());
		}
		if (dto.getDescription() != null) {
			book.setDescription(dto.getDescription());
		}
		
		return bookRepository.save(book);
	}
	
	public void delete(long id) throws BookNotFoundException {
		Book book = fetch(id);
		bookRepository.delete(book);
	}
	
	private Book fetch(long id) throws BookNotFoundException {
		Optional<Book> optionalBook = bookRepository.findById(id);
		if (optionalBook.isEmpty()) {
			throw new BookNotFoundException();
		}
		
		return optionalBook.get();
	}

}
