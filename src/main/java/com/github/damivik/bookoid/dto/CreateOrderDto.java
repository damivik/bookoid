package com.github.damivik.bookoid.dto;

import java.util.List;

public class CreateOrderDto {
	private long customerId;
	private List<CreateOrderDetailDto> createOrderDetailDtos;
	
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public CreateOrderDto() {
	}
	
	public CreateOrderDto(long customerId, List<CreateOrderDetailDto> createOrderDetailDtos) {
		this.customerId = customerId;
		this.createOrderDetailDtos = createOrderDetailDtos;
	}

	public List<CreateOrderDetailDto> getCreateOrderDetailDtos() {
		return createOrderDetailDtos;
	}

	public void setCreateOrderDetailDtos(List<CreateOrderDetailDto> createOrderDetailDtos) {
		this.createOrderDetailDtos = createOrderDetailDtos;
	}
		
}
