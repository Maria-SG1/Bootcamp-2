package com.example.domain.service;

import java.util.List;
import java.util.Optional;

import com.example.exceptions.DuplicateKeyException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domain.contracts.repository.CategoryRepository;
import com.example.domain.contracts.service.CategoryService;
import com.example.domain.entities.Actor;
import com.example.domain.entities.Category;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository dao;
	public CategoryServiceImpl(CategoryRepository dao) {
		this.dao = dao;
	}
	@Override
	public List<Category> getAll() {		
		return dao.findAll();
	}
	@Override
	public Optional<Category> getOne(Integer id) {
		return dao.findById(id);
	}
	@Override
	public Category add(Category item) throws DuplicateKeyException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("La categoría no puede ser nula.");
		}
//		if (item.getCategoryId()>0 && dao.existsById(item.getCategoryId())) {
//			throw new DuplicateKeyException("Ya existe.");
//		}
		if (dao.findById(item.getCategoryId()).isPresent()) {
			throw new DuplicateKeyException("Ya existe categoría con este id.");
		}
		return dao.save(item);
	}
	@Override
	public Category modify(Category item) throws ItemNotFoundException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("La categoría no puede ser nula.");
		}
		var categoria = dao.findById(item.getCategoryId());
		if (categoria.isPresent()) {
			Category c = categoria.get();
			c.setLastUpdate(item.getLastUpdate());
			c.setName(item.getName());
			return dao.save(c);
		} else {
			throw new ItemNotFoundException("No existe categoría con este ID."+item.getCategoryId());
		}	
	}
	@Override
	public void delete(Category item) throws InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("No puede ser nulo.");
		}
		dao.delete(item);
		
	}
	@Override
	public void deleteById(Integer id) throws ItemNotFoundException {
		if (!dao.findById(id).isPresent()) {
			throw new ItemNotFoundException("No existe categoría con ID:"+id);
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
	public Iterable<Category> getAll(Sort sort) {		
		return dao.findAll(sort);
	}
	@Override
	public Page<Category> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

}
