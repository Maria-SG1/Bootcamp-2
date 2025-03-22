package com.example.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domain.contracts.repository.ActorRepository;
import com.example.domain.contracts.service.ActorService;
import com.example.domain.entities.Actor;
import com.example.domain.entities.FilmActor;
import com.example.domain.entities.dto.ActorFilmDTO;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

@Service
public class ActorServiceImpl implements ActorService {

private ActorRepository dao;
	
	public ActorServiceImpl(ActorRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Actor> getAll() {		
		return dao.findAll();
	}

	@Override
	public Optional<Actor> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Actor add(Actor item) throws DuplicateKeyException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("El actor no puede ser nulo.");
		}
//		if (item.getFirstName().equals("") || item.getFirstName().isEmpty()
//				|| item.getLastName().equals("") || item.getLastName().isEmpty()) {
//			throw new InvalidDataException("Los nombres no pueden ser vacíos.");
//		}
//		if (item.getActorId()>0 && dao.existsById(item.getActorId())) {
//			throw new DuplicateKeyException("Ya existe actor con este id.");
//		}
		
		if (dao.findById(item.getActorId()).isPresent()) {
			throw new DuplicateKeyException("Ya existe actor con este id.");
		}
		return dao.save(item);
	}

	@Override
	public Actor modify(Actor item) throws ItemNotFoundException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("El actor no puede ser nulo.");
		}
		var actor = dao.findById(item.getActorId());
		if (actor.isPresent()) {
			Actor a = actor.get();
			a.setFirstName(item.getFirstName());
			a.setLastName(item.getLastName());
			a.setLastUpdate(item.getLastUpdate());
			a.setFilmActors(item.getFilmActors());
			return dao.save(a);
		} else {
			throw new ItemNotFoundException("No existe actor con id: "+item.getActorId());
		}	
	}

	@Override
	public void delete(Actor item) throws InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("No puede ser nulo.");
		}
		if (item.getFilmActors()!=null && !item.getFilmActors().isEmpty()) {
			throw new InvalidDataException("No se puede eliminar un actor que tiene películas asociadas.");
		}
		dao.delete(item);		
	}

	@Override
	public void deleteById(Integer id) throws ItemNotFoundException {
		if (!dao.findById(id).isPresent()) {
			throw new ItemNotFoundException("No existe actor con id: "+id);
		}
		dao.deleteById(id);
	}	
	
	@Override
	public ActorFilmDTO getActorsFilms(int id) throws ItemNotFoundException {
		Actor actor = dao.findById(id).orElse(null);
		if (actor != null) {
			List<String> films = actor.getFilmActors().stream()
					.map(FilmActor::getFilm)
					.map(film->film.getTitle())
					.collect(Collectors.toList());	
			return new ActorFilmDTO(actor.getFirstName() + " " + actor.getLastName(),films);
		} else {
			throw new ItemNotFoundException("Actor no encontrado.");
		}
		
	}

	@Override
	public <T> List<T> getByProjection(Class<T> type) {		
		return dao.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {		
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {		
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Iterable<Actor> getAll(Sort sort) {		
		return dao.findAll(sort);
	}

	@Override
	public Page<Actor> getAll(Pageable pageable) {	
		return dao.findAll(pageable);
	}

	@Override
	public List<Actor> novedades(Date fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
