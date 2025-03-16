package com.example.domain.contracts.service;

import com.example.domain.core.contracts.service.DomainService;
import com.example.domain.entities.Actor;
import com.example.domain.entities.dto.ActorFilmDTO;
import com.example.exceptions.ItemNotFoundException;

public interface ActorService  extends DomainService<Actor, Integer> {
	public ActorFilmDTO getActorsFilms(int id) throws ItemNotFoundException;
}
