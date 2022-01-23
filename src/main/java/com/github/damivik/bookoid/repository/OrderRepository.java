package com.github.damivik.bookoid.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.damivik.bookoid.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
