package com.github.damivik.bookoid.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.damivik.bookoid.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
