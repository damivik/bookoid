package com.github.damivik.bookoid.dto;

import java.util.List;

public class ViewOrderDto {
	private long id;
	private ViewCustomerDto customer;
	private List<ViewOrderDetailDto> items;

	public ViewOrderDto() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ViewCustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(ViewCustomerDto customer) {
		this.customer = customer;
	}

	public List<ViewOrderDetailDto> getItems() {
		return items;
	}

	public void setItems(List<ViewOrderDetailDto> items) {
		this.items = items;
	}
	
}
