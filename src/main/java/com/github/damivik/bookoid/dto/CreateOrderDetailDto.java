package com.github.damivik.bookoid.dto;

public class CreateOrderDetailDto {
	long bookId;
	int quantity;

	public CreateOrderDetailDto() {
	}

	public CreateOrderDetailDto(long bookId, int quantity) {
		this.bookId = bookId;
		this.quantity = quantity;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
