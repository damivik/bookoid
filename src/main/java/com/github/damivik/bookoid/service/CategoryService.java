package com.github.damivik.bookoid.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.damivik.bookoid.entity.Category;
import com.github.damivik.bookoid.exception.CategoryNotFoundException;
import com.github.damivik.bookoid.repository.CategoryRepository;

@Service
public class CategoryService {
	private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	private Category fetch(long id) throws CategoryNotFoundException {
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if (optionalCategory.isEmpty()) {
			throw new CategoryNotFoundException();
		}
		
		return optionalCategory.get();
	}
	
	public Category create(String name) {
		return categoryRepository.save(new Category(name));
	}
	
	public Category retrieve(long id) throws CategoryNotFoundException {
		return fetch(id);
	}
	
	public Category update(long id, String name) throws CategoryNotFoundException {
		Category category = fetch(id);
		category.setName(name);
		return categoryRepository.save(category);
	}
	
	public void delete(long id) throws CategoryNotFoundException {
		Category category = fetch(id);
		categoryRepository.delete(category);
	}
	
}
