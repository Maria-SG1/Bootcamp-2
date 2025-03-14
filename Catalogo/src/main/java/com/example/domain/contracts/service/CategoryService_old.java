package com.example.domain.contracts.service;

import java.util.List;
import java.util.Optional;

import com.example.domain.entities.Category;

public interface CategoryService_old {

	<S extends Category> S save(S entity);

	List<Category> findAll();

	Optional<Category> findById(Integer id);

	void delete(Category entity);

	void deleteAll();

}