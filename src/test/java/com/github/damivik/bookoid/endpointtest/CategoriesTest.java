package com.github.damivik.bookoid.endpointtest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.github.damivik.bookoid.entity.Category;

public class CategoriesTest extends Base {
	
	@Test
	void create() throws Exception {
		String name = "Memoirs";
		
		mvc
			.perform(
					post("/categories")
					.param("name", name))
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"))
			.andExpect(jsonPath("$.name", is(name)));
	}
	
	@Test
	void retrieve() throws Exception {
		Category category = createCategory();
		
		mvc
			.perform(
					get("/categories/" + category.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(category.getId().intValue())))
			.andExpect(jsonPath("$.name", is(category.getName())));
	}
	
	@Test
	void update() throws Exception {
		Category category = createCategory();
		String name = "Historical Fiction";
		
		mvc
			.perform(
					patch("/categories/" + category.getId())
					.param("name", name))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(category.getId().intValue())))
			.andExpect(jsonPath("$.name", is(name)));
	}
	
	@Test
	void delete() throws Exception {
		Category category = createCategory();
		
		mvc
			.perform(
					MockMvcRequestBuilders.delete("/categories/" + category.getId()))
			.andExpect(status().isOk());
	}
	
	@AfterEach
	public void tearDown() {
		refreshDatabse();
	}
	
}
