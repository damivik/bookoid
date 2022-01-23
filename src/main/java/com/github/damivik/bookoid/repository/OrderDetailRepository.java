package com.github.damivik.bookoid.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.damivik.bookoid.entity.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {
}
