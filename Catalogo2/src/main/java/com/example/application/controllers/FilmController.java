package com.example.application.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domain.contracts.service.FilmService;
import com.example.domain.entities.Film;
import com.example.domain.entities.dto.FilmDTO;
//import com.example.domain.entities.dto.FilmDTO;
import com.example.domain.entities.dto.FilmDetailsDTO;
import com.example.domain.entities.dto.FilmEditDTO;
import com.example.domain.entities.dto.FilmShort;
import com.example.domain.entities.dto.FilmShortDTO;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/peliculas/v1")
public class FilmController {
	private FilmService srv;

	public FilmController(FilmService srv) {
		super();
		this.srv = srv;
	}
	
	@GetMapping
	public List<FilmEditDTO> getAll() {
		return srv.getByProjection(FilmEditDTO.class);
	}
	
	@GetMapping("/short")
	public List<FilmShort> getAllShort() {
		return srv.getByProjection(FilmShort.class);
	}
	
//	@GetMapping(params= {"page"})
//	@Operation(summary="Obtiene películas paginadas")
//	public Page<FilmDTO> getAll(@ParameterObject Pageable pageable) {
//		return srv.getByProjection(pageable, FilmDTO.class);
//	}
	
	@GetMapping(params= {"page"})
	@Operation(summary="Obtiene peliculas paginados")
	public Page<FilmShort> getAll(@ParameterObject Pageable pageable) {
		return srv.getByProjection(pageable, FilmShort.class);
	}

//	@GetMapping(path = "/{id}")
//	@Operation(summary="Obtiene película por id")
//	public FilmDTO getOne(@PathVariable int id) throws ItemNotFoundException {
//		var item = srv.getOne(id);
//		if (item.isEmpty()) {
//			throw new ItemNotFoundException("No se encontró película con id " + id);
//		}
//		return FilmDTO.from(item.get());
//	}
	
	
	@GetMapping(path = "/{id}", params = "mode=short")
	public FilmShortDTO getOneCorto(
			@Parameter(description = "Identificador de la pelicula", required = true) 
			@PathVariable 
			int id,
			@Parameter(required = false, allowEmptyValue = true, schema = @Schema(type = "string", allowableValues = {
					"details", "short", "edit" }, defaultValue = "edit")) 
			@RequestParam(required = false, defaultValue = "edit") 
			String mode)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return FilmShortDTO.from(rslt.get());
	}

	@GetMapping(path = "/{id}", params = "mode=details")
	public FilmDetailsDTO getOneDetalle(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
			@Parameter(required = false, schema = @Schema(type = "string", allowableValues = { "details", "short",
					"edit" }, defaultValue = "edit")) @RequestParam(required = false, defaultValue = "edit") String mode)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return FilmDetailsDTO.from(rslt.get());
	}

	@Operation(summary = "Recupera una pelicula", description = "Están disponibles una versión corta, una versión con los detalles donde se han transformado las dependencias en cadenas y una versión editable donde se han transformado las dependencias en sus identificadores.")
	@GetMapping(path = "/{id}")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada", content = @Content(schema = @Schema(oneOf = {
			FilmShortDTO.class, FilmDetailsDTO.class, FilmEditDTO.class })))
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
	public FilmEditDTO getOneEditar(
			@Parameter(description = "Identificador de la pelicula", required = true) 
			@PathVariable 
			int id,
			@Parameter(required = false, schema = @Schema(type = "string", allowableValues = { "details", "short",
					"edit" }, defaultValue = "edit")) 
			@RequestParam(required = false, defaultValue = "edit") 
			String mode)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return FilmEditDTO.from(rslt.get());
	}
	
	record Nombre(int id, String firstName, String lastName) { }  	
	@GetMapping(path = "/{id}/actores")
	@Transactional  
	public List<Nombre> getActores(@PathVariable int id) throws ItemNotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty()) {
			throw new ItemNotFoundException("No se encontró película con id " + id);
		}
		return item.get().getFilmActors().stream()
				.map(o->new Nombre(o.getActor().getActorId(), o.getActor().getFirstName(), o.getActor().getLastName()))
				.toList();
	}
	
	record Cat(int id, String name) { }  	
	@GetMapping(path = "/{id}/categorias")
	@Transactional  
	public List<Cat> getCategorias(@PathVariable int id) throws ItemNotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty()) {
			throw new ItemNotFoundException("No se encontró película con id " + id);
		}
		return item.get().getFilmCategories().stream()
				.map(o->new Cat(o.getCategory().getCategoryId(), o.getCategory().getName()))
				.toList();
	}
	
	@PostMapping
	@ApiResponse(responseCode="201", description="Película creada")
	public ResponseEntity<Object> create(@Valid @RequestBody FilmDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(FilmDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
//	@PostMapping
//	@ApiResponse(responseCode="201", description="Película creada")
//	public ResponseEntity<Object> create(@Valid @RequestBody FilmEditDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
//		var newItem = srv.add(FilmEditDTO.from(item));
//		System.err.println(newItem);
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//			.buildAndExpand(newItem.getFilmId()).toUri();
//		return ResponseEntity.created(location).build();
//	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody FilmEditDTO item) throws BadRequestException, ItemNotFoundException, InvalidDataException {
		if (item.getFilmId() != id) {
			throw new BadRequestException("El id de la película no coincide con el recurso a modificar");
		}
		srv.modify(FilmEditDTO.from(item));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) throws ItemNotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty()) {
			throw new ItemNotFoundException("No se encontró la película con id " + id);
		}
		srv.deleteById(id);
	}
	
	
	@GetMapping("/{length1}/{length2}")
	public List<FilmEditDTO> findByLengthBetween(@PathVariable int length1, @PathVariable int length2) {
		srv.findByLengthBetween(length1, length2);		
		return srv.findByLengthBetween(length1, length2)
				.stream().map(FilmEditDTO::from).toList();	
	}
	
}
