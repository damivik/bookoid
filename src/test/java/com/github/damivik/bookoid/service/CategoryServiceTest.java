package com.github.damivik.bookoid.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.github.damivik.bookoid.entity.Category;
import com.github.damivik.bookoid.repository.CategoryRepository;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;
	
	@Test
	void create() {
		String categoryName = "Fiction";
		Category expectedCategory = new Category();
		Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(expectedCategory);
		CategoryService categoryService = new CategoryService(categoryRepository);
		ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
		
		Category actualCategory = categoryService.create(categoryName);
		
		Mockito.verify(categoryRepository).save(captor.capture());
		assertSame(expectedCategory, actualCategory);
		assertEquals(categoryName, captor.getValue().getName());
	}

	@Test
	void retrieve() throws Exception {
		long id = 3;
		Category expectedCategory = new Category();
		Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(expectedCategory));
		CategoryService categoryService = new CategoryService(categoryRepository);
		
		Category actualCategory = categoryService.retrieve(id);
		
		assertSame(expectedCategory, actualCategory);
	}
	
	@Test
	void update() throws Exception {
		String name = "Fiction";
		long id = 1;
		Category category = new Category();
		Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
		CategoryService categoryService = new CategoryService(categoryRepository);
		
		categoryService.update(id, name);
		
		assertEquals(name, category.getName());
	}
	
	@Test
	void delete() throws Exception {
		long id = 1;
		Category category = new Category();
		Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
		CategoryService categoryService = new CategoryService(categoryRepository);
		
		categoryService.delete(id);
		
		Mockito.verify(categoryRepository).delete(category);
	}
	
}
