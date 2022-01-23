package com.github.damivik.bookoid.endpointtest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damivik.bookoid.dto.CreateBookDto;
import com.github.damivik.bookoid.dto.UpdateBookDto;
import com.github.damivik.bookoid.entity.Book;
import com.github.damivik.bookoid.entity.Category;

public class BooksTest extends Base {
	
	@Test
	void create() throws Exception {
		String title = "1984";
		String authors = "Geroge Orwell";
		BigDecimal price = new BigDecimal("5500");
		int quantity = 100;
		Category fiction = createCategory("Fiction");
		Category classics = createCategory("Classics");
		List<Long> categories = Arrays.asList(fiction.getId(), classics.getId());
		String description = "Nineteen Eighty-Four is a rare work that grows more haunting as its futuristic "
				+ "purgatory becomes more real...";
		CreateBookDto dto = new CreateBookDto(title, authors, price, quantity, categories, description);
		String payload = new ObjectMapper().writeValueAsString(dto);
		
		mvc
			.perform(
					post("/books")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(payload))
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"))
			.andExpect(jsonPath("$.title", is(title)))
			.andExpect(jsonPath("$.authors", is(authors)))
			.andExpect(jsonPath("$.price", is(price.intValue())))
			.andExpect(jsonPath("$.description", is(description)))
			.andExpect(jsonPath("$.categories[0].id", is(fiction.getId().intValue())))
			.andExpect(jsonPath("$.categories[0].name", is(fiction.getName())))
			.andExpect(jsonPath("$.categories[1].id", is(classics.getId().intValue())))
			.andExpect(jsonPath("$.categories[1].name", is(classics.getName())));
	}
	
	@Test
	void retrieve() throws Exception {
		Book book = createBook();
		
		mvc
			.perform(
					get("/books/" + book.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title", is(book.getTitle())))
			.andExpect(jsonPath("$.authors", is(book.getAuthors())))
			.andExpect(jsonPath("$.price", is(book.getPrice().doubleValue())))
			.andExpect(jsonPath("$.description", is(book.getDescription())))
			.andExpect(jsonPath("$.categories[0].id", is(book.getCategories().get(0).getId().intValue())))
			.andExpect(jsonPath("$.categories[0].name", is(book.getCategories().get(0).getName())))
			.andExpect(jsonPath("$.categories[1].id", is(book.getCategories().get(1).getId().intValue())))
			.andExpect(jsonPath("$.categories[1].name", is(book.getCategories().get(1).getName())));
	}
	
	@Test
	void update() throws Exception {
		Book book = createBook();
		BigDecimal price = new BigDecimal("6000.99");
		UpdateBookDto dto = new UpdateBookDto();
		dto.setPrice(price);
		String payload = new ObjectMapper().writeValueAsString(dto);
		
		mvc
			.perform(
					patch("/books/" + book.getId())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(payload))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.price", is(price.doubleValue())));
	}

	@Test
	void delete() throws Exception {
		Book book = createBook();
				
		mvc
			.perform(
					MockMvcRequestBuilders.delete("/books/" + book.getId()))
			.andExpect(status().isOk());
	}

	@AfterEach
	public void tearDown() {
		refreshDatabse();
	}
	
}
