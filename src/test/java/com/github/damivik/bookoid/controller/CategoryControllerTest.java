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

import com.github.damivik.bookoid.dto.ViewCategoryDto;
import com.github.damivik.bookoid.entity.Category;
import com.github.damivik.bookoid.service.CategoryService;
import com.github.damivik.bookoid.util.Mapper;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class CategoryControllerTest {
	
	@Mock
	private CategoryService categoryService;
	
	@Mock
	private Mapper mapper;
	
	@Test
	void create() throws Exception {
		long id = 1;
		Category category = new Category();
		category.setId(id);
		ViewCategoryDto dto = new ViewCategoryDto();
		URI location = URI.create("/categories/" + id);
		String name = "Fiction";
		Mockito.when(categoryService.create(name)).thenReturn(category);
		Mockito.when(mapper.mapCategoryToViewCategoryDto(category)).thenReturn(dto);
		CategoryController controller = new CategoryController(categoryService, mapper);
		
		ResponseEntity<ViewCategoryDto> responseEntity = controller.create(name);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(location, responseEntity.getHeaders().getLocation());
		assertEquals(dto, responseEntity.getBody());
	}

	@Test
	void retrieve() throws Exception {
		long id = 1;
		Category category = new Category();
		ViewCategoryDto expectedDto = new ViewCategoryDto();
		Mockito.when(categoryService.retrieve(id)).thenReturn(category);
		Mockito.when(mapper.mapCategoryToViewCategoryDto(category)).thenReturn(expectedDto);
		CategoryController controller = new CategoryController(categoryService, mapper);
		
		ViewCategoryDto actualDto = controller.retrieve(id);
		
		assertSame(expectedDto, actualDto);
	}
	
	@Test
	void update() throws Exception {
		long id = 1;
		String name = "Fiction";
		Category category = new Category();
		ViewCategoryDto expectedDto = new ViewCategoryDto();
		Mockito.when(categoryService.update(id, name)).thenReturn(category);
		Mockito.when(mapper.mapCategoryToViewCategoryDto(category)).thenReturn(expectedDto);
		CategoryController controller = new CategoryController(categoryService, mapper);
		
		ViewCategoryDto actualDto = controller.update(id, name);
		
		Mockito.verify(categoryService).update(id, name);
		assertSame(expectedDto, actualDto);
	}
	
	@Test
	void delete() throws Exception {
		long id = 1;
		CategoryController controller = new CategoryController(categoryService, mapper);
		
		controller.delete(id);
		
		Mockito.verify(categoryService).delete(id);
	}
}
