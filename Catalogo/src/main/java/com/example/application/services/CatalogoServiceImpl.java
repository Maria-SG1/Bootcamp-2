package com.example.application.services;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.contracts.CatalogoService;
import com.example.application.models.NovedadesDTO;
import com.example.domain.contracts.service.ActorService;
import com.example.domain.contracts.service.CategoryService;
import com.example.domain.contracts.service.FilmService;
import com.example.domain.contracts.service.LanguageService;
import com.example.domain.entities.dto.FilmShortDTO;
import com.example.domain.entities.dto.ActorDTO;

@Service
public class CatalogoServiceImpl implements CatalogoService{
	@Autowired
	private FilmService fs;
	@Autowired
	private ActorService as;
	@Autowired
	private CategoryService cs;
	@Autowired
	private LanguageService ls;
	
	@Override
	public NovedadesDTO novedades(Date fecha) {
		if(fecha == null)
			fecha = Date.from(Instant.now().minusSeconds(36000));
		return new NovedadesDTO(
				fs.novedades(fecha).stream().map(item -> new FilmShortDTO(item.getFilmId(), item.getTitle())).toList(), 
				as.novedades(fecha).stream().map(item -> ActorDTO.from(item)).toList(), 
				cs.novedades(fecha), 
				ls.novedades(fecha)
				);
	}
	
	
	
	
}
