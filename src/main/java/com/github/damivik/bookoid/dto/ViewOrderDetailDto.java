package com.github.damivik.bookoid.dto;

public class ViewOrderDetailDto {
	String book; // book uri /books/{bookId}
	int quantity;

	public ViewOrderDetailDto() {
	}

	public ViewOrderDetailDto(String book, int quantity) {
		this.book = book;
		this.quantity = quantity;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}