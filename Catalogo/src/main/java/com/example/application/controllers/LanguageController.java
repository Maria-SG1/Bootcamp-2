package com.example.application.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.contracts.service.LanguageService;
import com.example.domain.entities.dto.CategoryDTO;
import com.example.domain.entities.dto.LanguageDTO;
import com.example.exceptions.ItemNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/languages/v1")
public class LanguageController {
	private LanguageService srv;

	public LanguageController(LanguageService srv) {
		super();
		this.srv = srv;
	}	

	@GetMapping
	@Operation(summary="Obtener todos los idiomas")
	public List<LanguageDTO> getAll() {
		return srv.getByProjection(LanguageDTO.class);
	}
	
	@GetMapping(path = "/{id}")
	@Operation(summary="Obtener idioma por id")
	public LanguageDTO getOne(@PathVariable @Parameter(description="Identificador") int id) throws ItemNotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty()) {
			throw new ItemNotFoundException("No se encontró idioma con id " + id);
		}
		return LanguageDTO.from(item.get());
	}
}
