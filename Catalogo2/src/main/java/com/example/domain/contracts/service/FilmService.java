package com.example.domain.contracts.service;

import java.util.Date;
import java.util.List;

import com.example.domain.core.contracts.service.ProjectionDomainService;
import com.example.domain.entities.Film;
//import com.example.domain.entities.dto.FilmDTO;

public interface FilmService extends ProjectionDomainService<Film, Integer> {
	List<Film> findByLengthBetween(int val1, int val2);
	List<Film> novedades(Date fecha);
	
//	Film createFilmWithActors(FilmDTO filmDTO);
}
