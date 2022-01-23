package com.github.damivik.bookoid.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.damivik.bookoid.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public boolean existsByEmail(String email);
}
