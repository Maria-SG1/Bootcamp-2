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

import com.example.domains.contracts.services.CategoryService;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias/v1")
public class CategoryController {
	private CategoryService srv;

	public CategoryController(CategoryService srv) {
		super();
		this.srv = srv;
	}
	
	@GetMapping
	public List<CategoryDTO> getAll() {
		return srv.getByProjection(CategoryDTO.class);
	}
	
	@GetMapping(path = "/{id}")
	@Operation(summary="Obtiene categoría por id")
	public CategoryDTO getOne(@PathVariable @Parameter(description="Identificador del actor") int id) throws ItemNotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty()) {
			throw new ItemNotFoundException("No se encontró categoría con id " + id);
		}
		return CategoryDTO.from(item.get());
	}
	
	@PostMapping
	@ApiResponse(responseCode="201", description="Categoría creada")
	public ResponseEntity<Object> create(@Valid @RequestBody CategoryDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(CategoryDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getCategoryId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody CategoryDTO item) throws BadRequestException, ItemNotFoundException, InvalidDataException {
		if (item.getCategoryId() != id) {
			throw new BadRequestException("El id de la categoría no coincide con el recurso a modificar");
		}	
		
//		try {
//			srv.modify(CategoryDTO.from(item));
//		} catch(Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
		srv.modify(CategoryDTO.from(item));
	}	

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) throws ItemNotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty()) {
			throw new ItemNotFoundException("No se encontró la categoría con id " + id);
		}
		srv.deleteById(id);
	}

}
