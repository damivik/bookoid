package com.github.damivik.bookoid.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.damivik.bookoid.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
