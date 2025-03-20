package com.example.domain.contracts.service;

import java.util.List;

import com.example.domain.core.contracts.service.ProjectionDomainService;
import com.example.domain.entities.Film;

public interface FilmService extends ProjectionDomainService<Film, Integer> {
	List<Film> findByLengthBetween(int val1, int val2);
}
