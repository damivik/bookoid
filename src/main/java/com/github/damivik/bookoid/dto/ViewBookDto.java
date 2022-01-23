package com.github.damivik.bookoid.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ViewBookDto {
	private Long id;
	
	private String title;
	
	private String authors;
	
	private BigDecimal price;
	
	private Integer quantity;
	
	private List<ViewCategoryDto> categories;
	
	private String description;
	
	private LocalDate publishedDate;

	public ViewBookDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<ViewCategoryDto> getCategories() {
		return categories;
	}

	public void setCategories(List<ViewCategoryDto> categories) {
		this.categories = categories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}
	
}
