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

import com.github.damivik.bookoid.entity.User;

public class CustomersTest extends Base {
	
	@Test
	void create() throws Exception {
		String email = "dami@mail.com";
		String firstName = "Dami";
		String lastName = "Vik";
		String password = "password";
		
		mvc
			.perform(
					post("/customers")
					.param("email", email)
					.param("firstName", firstName)
					.param("lastName", lastName)
					.param("password", password))
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"))
			.andExpect(jsonPath("$.email", is(email)))
			.andExpect(jsonPath("$.firstName", is(firstName)))
			.andExpect(jsonPath("$.lastName", is(lastName)));
	}

	@Test
	void retrieve() throws Exception {
		User user = createCustomer();
		
		mvc
			.perform(get("/customers/" + user.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(user.getId().intValue())))
			.andExpect(jsonPath("$.email", is(user.getEmail())))
			.andExpect(jsonPath("$.firstName", is(user.getFirstName())))
			.andExpect(jsonPath("$.lastName", is(user.getLastName())));
	}
	
	@Test
	void update() throws Exception {
		User user = createCustomer();
		String firstName = "new_first_name";
		String lastName = "new_last_name";
		String password = "new_password";
		
		mvc
			.perform(
					patch("/customers/" + user.getId())
					.param("firstName", firstName)
					.param("lastName", lastName)
					.param("password", password))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(user.getId().intValue())))
			.andExpect(jsonPath("$.email", is(user.getEmail())))
			.andExpect(jsonPath("$.firstName", is(firstName)))
			.andExpect(jsonPath("$.lastName", is(lastName)));
	}
	
	@Test
	void delete() throws Exception {
		User user = createCustomer();
		
		mvc
			.perform(
					MockMvcRequestBuilders.delete("/customers/" + user.getId()))
			.andExpect(status().isOk());
	}
	
	@AfterEach
	public void tearDown() {
		refreshDatabse();
	}
	
}
