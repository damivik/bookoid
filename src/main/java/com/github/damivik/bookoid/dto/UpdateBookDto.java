package com.github.damivik.bookoid.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Size;

public class UpdateBookDto {
	
	@Size(max = 255)
	private String title;
	
	@Size(max = 255)
	private String authors;
	
	private BigDecimal price;
	
	private Integer quantity;
	
	@Size(max = 1000)
	private String description;

	public UpdateBookDto() {
	}

	public UpdateBookDto(@Size(max = 255) String title, @Size(max = 255) String authors, BigDecimal price,
			Integer quantity, String description) {
		this.title = title;
		this.authors = authors;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
