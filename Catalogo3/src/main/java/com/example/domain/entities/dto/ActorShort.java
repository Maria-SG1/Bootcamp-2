package com.example.domain.entities.dto;

import org.springframework.beans.factory.annotation.Value;

public interface ActorShort {
	@Value("#{target.actorId}")
	int getId();
	@Value("#{'Nombre: ' + target.firstName + ' ' + target.lastName}")
	String getFullName();
}
