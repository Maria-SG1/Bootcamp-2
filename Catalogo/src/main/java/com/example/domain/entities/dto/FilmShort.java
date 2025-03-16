package com.example.domain.entities.dto;

import org.springframework.beans.factory.annotation.Value;

public interface FilmShort {
	@Value("#{target.filmId}")
	int getId();
	@Value("#{'Título: ' + target.title}")
	String getTitle();
	@Value("#{'Idioma principal: ' + (target.languageVO!=null ? target.languageVO.name : 'Sin idioma')}")
	String getLanguages();
}
