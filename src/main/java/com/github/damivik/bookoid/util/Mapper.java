package com.github.damivik.bookoid.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.damivik.bookoid.dto.ViewBookDto;
import com.github.damivik.bookoid.dto.ViewCategoryDto;
import com.github.damivik.bookoid.dto.ViewCustomerDto;
import com.github.damivik.bookoid.dto.ViewOrderDetailDto;
import com.github.damivik.bookoid.dto.ViewOrderDto;
import com.github.damivik.bookoid.entity.Book;
import com.github.damivik.bookoid.entity.Category;
import com.github.damivik.bookoid.entity.Order;
import com.github.damivik.bookoid.entity.OrderDetail;
import com.github.damivik.bookoid.entity.User;

@Component
public class Mapper {
	public ViewCustomerDto mapUserToViewCustomerDto(User user) {
		return new ViewCustomerDto(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
	}

	public ViewCategoryDto mapCategoryToViewCategoryDto(Category category) {
		return new ViewCategoryDto(category.getId(), category.getName());
	}

	public ViewBookDto mapBookToViewBookDto(Book book) {
		ViewBookDto dto = new ViewBookDto();
		dto.setId(book.getId());
		dto.setTitle(book.getTitle());
		dto.setAuthors(book.getAuthors());
		dto.setPrice(book.getPrice());
		dto.setQuantity(book.getQuantity());
		dto.setDescription(book.getDescription());
		List<ViewCategoryDto> categories = new ArrayList<>();
		for (Category category : book.getCategories()) {
			categories.add(mapCategoryToViewCategoryDto(category));
		}
		dto.setCategories(categories);

		return dto;
	}

	public ViewOrderDto mapOrderToViewOrderDto(Order order) {
		ViewOrderDto dto = new ViewOrderDto();
		dto.setId(order.getId());
		dto.setCustomer(mapUserToViewCustomerDto(order.getCustomer()));
		ArrayList<ViewOrderDetailDto> orderItems = new ArrayList<>();
		for (OrderDetail orderDetail : order.getOrderDetails()) {
			String bookUri = "/books/" + orderDetail.getBook().getId();
			ViewOrderDetailDto viewOrderDetailDto = new ViewOrderDetailDto(bookUri, orderDetail.getQuantity());
			orderItems.add(viewOrderDetailDto);
		}
		dto.setItems(orderItems);

		return dto;
	}
}
