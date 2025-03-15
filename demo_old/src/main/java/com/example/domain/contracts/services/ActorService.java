package com.example.domain.contracts.services;

import com.example.domain.core.contracts.service.DomainService;
import com.example.domain.entities.Actor;

public interface ActorService extends DomainService<Actor, Integer> {
	void repartePremios();
}
