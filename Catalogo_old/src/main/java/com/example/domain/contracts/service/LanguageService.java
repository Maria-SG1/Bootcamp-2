package com.example.domain.contracts.service;

import java.util.Date;
import java.util.List;

import com.example.domain.core.contracts.service.ProjectionDomainService;
import com.example.domain.entities.Language;

public interface LanguageService extends ProjectionDomainService<Language, Integer> {
	List<Language> novedades(Date fecha);
}
