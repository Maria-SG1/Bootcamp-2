package com.example.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domain.contracts.repository.LanguageRepository;
import com.example.domain.contracts.service.LanguageService;
import com.example.domain.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

@Service
public class LanguageServiceImpl implements LanguageService {

	private LanguageRepository dao;
	
	public LanguageServiceImpl(LanguageRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Language> getAll() {		
		return dao.findAll();
	}

	@Override
	public Optional<Language> getOne(Integer id) {		
		return dao.findById(id);
	}

	@Override
	public Language add(Language item) throws DuplicateKeyException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("El idioma no puede ser nulo.");
		}
		if (dao.findById(item.getLanguageId()).isPresent()) {
			throw new DuplicateKeyException("Ya existe idioma con este id.");
		}
		return dao.save(item);
	}

	@Override
	public Language modify(Language item) throws ItemNotFoundException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("El idioma no puede ser nulo.");
		}
		var idioma = dao.findById(item.getLanguageId());
		if (idioma.isPresent()) {
			Language lang = idioma.get();
			lang.setName(item.getName());			
			return dao.save(lang);
		} else {
			throw new ItemNotFoundException("No existe idioma con este ID."+item.getLanguageId());
		}	
	}

	@Override
	public void delete(Language item) throws InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("No puede ser nulo.");
		}
		dao.delete(item);

	}

	@Override
	public void deleteById(Integer id) throws ItemNotFoundException {
		if (!dao.findById(id).isPresent()) {
			throw new ItemNotFoundException("No existe categoría con ID: "+id);
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
	public Iterable<Language> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Language> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Language> novedades(Date fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
