package com.example.domain.contracts.service;

import java.util.Date;
import java.util.List;

import com.example.domain.core.contracts.service.ProjectionDomainService;
import com.example.domain.entities.Category;


public interface CategoryService extends ProjectionDomainService<Category, Integer> {
	List<Category> novedades(Date fecha);
}
