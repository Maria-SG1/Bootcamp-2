package com.example.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.domain.contracts.repository.FilmRepository;
import com.example.domain.contracts.service.FilmService;
import com.example.domain.entities.Film;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

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
		if (item.getFilmId()>0 && dao.existsById(item.getFilmId())) {
			throw new DuplicateKeyException("Ya existe.");
		}
		return dao.save(item);
	}

	@Override
	public Film modify(Film item) throws ItemNotFoundException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("El film no puede ser nulo.");
		}
		var film = dao.findById(item.getFilmId());
		if (film.isPresent()) {
			var f = film.get();
			f.setDescription(item.getDescription());
			f.setLastUpdate(item.getLastUpdate());
			f.setLength(item.getLength());
			f.setRating(item.getRating());
			f.setReleaseYear(item.getReleaseYear());
			f.setRentalDuration(item.getRentalDuration());
			f.setRentalRate(item.getRentalRate());
			f.setReplacementCost(item.getReplacementCost());
			f.setTitle(item.getTitle());
			f.setLanguage2(item.getLanguage2());
			f.setLanguageVO(item.getLanguageVO());
			f.setFilmActors(item.getFilmActors());
			f.setFilmCategories(item.getFilmCategories());
			return dao.save(item);
		} else {
			throw new ItemNotFoundException("No existe film con este ID.");
		}	
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

}
