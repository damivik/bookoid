package com.github.damivik.bookoid.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.damivik.bookoid.dto.ViewCategoryDto;
import com.github.damivik.bookoid.entity.Category;
import com.github.damivik.bookoid.exception.CategoryNotFoundException;
import com.github.damivik.bookoid.service.CategoryService;
import com.github.damivik.bookoid.util.Mapper;

@RestController
public class CategoryController {
	private CategoryService categoryService;
	private Mapper mapper;
	
	public CategoryController(CategoryService categoryService, Mapper mapper) {
		this.categoryService = categoryService;
		this.mapper = mapper;
	}
	
	@PostMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ViewCategoryDto> create(@RequestParam String name) {
		Category category = categoryService.create(name);
		URI location = URI.create("/categories/" + category.getId());
		return ResponseEntity.created(location).body(mapper.mapCategoryToViewCategoryDto(category));
	}
	
	@GetMapping(path = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ViewCategoryDto retrieve(@PathVariable long id) {
		try {
			Category category = categoryService.retrieve(id);
			return mapper.mapCategoryToViewCategoryDto(category);
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping(path = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ViewCategoryDto update(@PathVariable long id, @Valid @NotBlank @RequestParam String name) {
		try {
			Category category = categoryService.update(id, name);
			return mapper.mapCategoryToViewCategoryDto(category);
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/categories/{id}")
	public void delete(@PathVariable long id) {
		try {
			categoryService.delete(id);
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
}
