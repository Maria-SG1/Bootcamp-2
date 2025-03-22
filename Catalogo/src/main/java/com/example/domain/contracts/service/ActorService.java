package com.example.domain.contracts.service;

import java.util.Date;
import java.util.List;

import com.example.domain.core.contracts.service.ProjectionDomainService;
import com.example.domain.entities.Actor;
import com.example.domain.entities.dto.ActorFilmDTO;
import com.example.exceptions.ItemNotFoundException;

public interface ActorService  extends ProjectionDomainService<Actor, Integer> {
	public ActorFilmDTO getActorsFilms(int id) throws ItemNotFoundException;
	List<Actor> novedades(Date fecha);
}
