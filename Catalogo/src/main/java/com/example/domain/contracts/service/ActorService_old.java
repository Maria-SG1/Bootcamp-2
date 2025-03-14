package com.example.domain.contracts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;

import com.example.domain.entities.Actor;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

public interface ActorService_old {

	<S extends Actor> S save(S entity) throws DuplicateKeyException, InvalidDataException;

	List<Actor> findAll();

	Optional<Actor> findById(Integer id);

	void delete(Actor entity) throws InvalidDataException;

	void deleteAll();
	
	<S extends Actor> S modify(S entity) throws ItemNotFoundException, InvalidDataException;

}