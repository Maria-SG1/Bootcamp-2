package com.example.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;

import com.example.domain.contracts.repository.LanguageRepository;
import com.example.domain.contracts.service.LanguageService;
import com.example.domain.entities.Language;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Language modify(Language item) throws ItemNotFoundException, InvalidDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Language item) throws InvalidDataException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) throws ItemNotFoundException {
		// TODO Auto-generated method stub

	}

}
