package com.github.damivik.bookoid.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateBookDto {

	@NotBlank
	@Size(max = 255)
	private String title;

	@NotBlank
	@Size(max = 255)
	private String authors;

	@NotNull
	private BigDecimal price;

	@NotNull
	private Integer quantity;

	private List<Long> categoryIds;

	@Size(max = 1000)
	private String description;

	public CreateBookDto() {
	}

	public CreateBookDto(String title, String authors, BigDecimal price, Integer quantity, List<Long> categoryIds,
			String description) {
		this.title = title;
		this.authors = authors;
		this.price = price;
		this.quantity = quantity;
		this.categoryIds = categoryIds;
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

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
