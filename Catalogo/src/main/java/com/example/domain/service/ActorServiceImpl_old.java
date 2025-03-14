package com.example.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.domain.contracts.repository.ActorRepository;
import com.example.domain.contracts.service.ActorService_old;
import com.example.domain.entities.Actor;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

@Service
public class ActorServiceImpl_old implements ActorService_old {
	
	private ActorRepository dao;
	
	public ActorServiceImpl_old(ActorRepository dao) {
		this.dao = dao;
	}
	
	@Override
	public Actor save(Actor entity) throws InvalidDataException, DuplicateKeyException {
		if (entity == null) {
			throw new InvalidDataException("El actor no puede ser nulo.");
		}
		if (entity.getActorId()>0 && dao.existsById(entity.getActorId())) {
			throw new DuplicateKeyException("Ya existe.");
		}
		return dao.save(entity);
	}

	@Override
	public List<Actor> findAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Actor> findById(Integer id) {
		return dao.findById(id);
	}	

	@Override
	public void delete(Actor entity) throws InvalidDataException {
		if (entity == null) {
			throw new InvalidDataException("No puede ser nulo.");
		}
		dao.delete(entity);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
	}

	@Override
	public Actor modify(Actor entity) throws InvalidDataException, ItemNotFoundException {
		if (entity == null) {
			throw new InvalidDataException("El actor no puede ser nulo.");
		}
		var actor = dao.findById(entity.getActorId());
		if (actor.isPresent()) {
			Actor a = actor.get();
			a.setFirstName(entity.getFirstName());
			a.setLastName(entity.getLastName());
			a.setLastUpdate(entity.getLastUpdate());
			return dao.save(a);
		} else {
			throw new ItemNotFoundException("No existe actor con este ID.");
		}
		
		
	}	

}
