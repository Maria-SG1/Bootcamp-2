package com.example.application.controllers;

import java.net.URI;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domain.contracts.service.LanguageService;
import com.example.domain.entities.dto.CategoryDTO;
import com.example.domain.entities.dto.LanguageDTO;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

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
	
	@PostMapping
	@ApiResponse(responseCode="201", description="Idioma creado")
	public ResponseEntity<Object> create(@Valid @RequestBody LanguageDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(LanguageDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getLanguageId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody LanguageDTO item) throws BadRequestException, ItemNotFoundException, InvalidDataException {
		if (item.getLanguageId() != id) {
			throw new BadRequestException("El id del idioma no coincide con el recurso a modificar");
		}	
		srv.modify(LanguageDTO.from(item));
	}	

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) throws ItemNotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty()) {
			throw new ItemNotFoundException("No se encontró el idioma con id " + id);
		}
		srv.deleteById(id);
	}
}
