package com.example.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domain.contracts.repository.FilmRepository;
import com.example.domain.contracts.service.FilmService;
import com.example.domain.entities.Film;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class FilmServiceImpl implements FilmService {

	private FilmRepository dao;
	
	public FilmServiceImpl(FilmRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Film> getAll() {		
		return dao.findAll();
	}

	@Override
	public Optional<Film> getOne(Integer id) {		
		return dao.findById(id);
	}

	@Override
	public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("El film no puede ser nulo.");
		}
		
		if (dao.findById(item.getFilmId()).isPresent()) {
			throw new DuplicateKeyException("Ya existe película con este id.");
		}
		if (item.getFilmId() != 0) { 
	        throw new InvalidDataException("El ID de la película debe ser 0 para que se genere automáticamente.");
	    }
		return dao.save(item);
	}
	
	@Override
	@Transactional
	public Film modify(Film item) throws ItemNotFoundException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("El film no puede ser nulo.");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		var leido = dao.findById(item.getFilmId()).orElseThrow(() -> new ItemNotFoundException());
		return dao.save(item.merge(leido));
	}

	@Override
	public void delete(Film item) throws InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("No puede ser nulo.");
		}
		dao.delete(item);
	}

	@Override
	public void deleteById(Integer id) throws ItemNotFoundException {
		if (!dao.findById(id).isPresent()) {
			throw new ItemNotFoundException("No existe film con id: "+id);
		}
		dao.deleteById(id);
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
	public Iterable<Film> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Film> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Film> findByLengthBetween(int val1, int val2) {		
		return dao.findByLengthBetween(val1, val2);
	}

	@Override
	public List<Film> novedades(Date fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
