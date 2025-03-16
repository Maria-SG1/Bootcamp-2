package com.example.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.domain.contracts.repository.ActorRepository;
import com.example.domain.service.ActorServiceImpl;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class ActorTest {
	
	private Validator validator;	
	private ActorRepository ar; 
	private ActorServiceImpl as; 

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setup() throws Exception {
		ar = mock(ActorRepository.class); 
		as = new ActorServiceImpl(ar); 		
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	@DisplayName("Obtener todos actores")
	public void getAll() {		
		when(ar.findAll()).thenReturn(List.of(mock(Actor.class)));
		List<Actor> actores = as.getAll();
		assertNotNull(actores);
		assertEquals(1, actores.size());
		verify(ar).findAll(); 
	}
	
	@Test
	@DisplayName("Obtener todos actores vacío")
	public void getAllVacio() {			
		when(ar.findAll()).thenReturn(Collections.emptyList());
		List<Actor> actores = as.getAll();
		assertNotNull(actores);
		assertEquals(0, actores.size());
		verify(ar).findAll(); 
	}
	
	@Test
	@DisplayName("Buscar actor por id")
	public void getOne() {		
		var id = 201;	
		when(ar.findById(id)).thenReturn(Optional.of(mock(Actor.class)));
		Optional<Actor> actor = as.getOne(id);
		assertTrue(actor.isPresent());
		verify(ar).findById(id);
	}
	
	@Test 
	@DisplayName("Buscar actor por id inexistente")
	public void getOneInexistente() {
		var id = 1000;
		when(ar.findById(id)).thenReturn(Optional.empty());
		Optional<Actor> actor = as.getOne(id);
		assertTrue(actor.isEmpty());
		verify(ar).findById(id);
	}

	@Test
	@DisplayName("Añadir actor")
	public void testAddActor() throws DuplicateKeyException, InvalidDataException {
		var actor = new Actor(0, "Irene", "Mayorino");
		when(ar.save(any(Actor.class))).thenReturn(actor);
		var addedActor = as.add(actor);
		assertEquals("Irene", addedActor.getFirstName());		
		verify(ar).save(any(Actor.class)); 
	}
	
	@Test
	@DisplayName("Añadir clave duplicada")
	public void testAddClaveDuplicada() throws DuplicateKeyException, InvalidDataException {
		var actor = new Actor(10, "Irene", "Mayorino");
//		when(ar.findById(10)).thenReturn(Optional.of(ar.findById(10).get()));
		when(ar.findById(10)).thenReturn(Optional.of(new Actor(10,"Pepito","Perez")));
		var ex = assertThrows(DuplicateKeyException.class, ()->as.add(actor));
		assertEquals("Ya existe actor con este id.", ex.getMessage());
		verify(ar, never()).save(any(Actor.class));
	}
	
	@Test
	@DisplayName("Añadir datos inválidos")
	public void testAddInvalidData() throws DuplicateKeyException, InvalidDataException  {
		var nullex = assertThrows(InvalidDataException.class, ()->as.add(null));
		assertEquals("El actor no puede ser nulo.", nullex.getMessage());
		verify(ar, never()).save(any(Actor.class));
	}
	
//	@Test
//	public void testAddNombreVacio() throws DuplicateKeyException, InvalidDataException  {
//		var actor = new Actor(0, "", "Apellido");
//		var ex = assertThrows(InvalidDataException.class, ()->as.add(actor));
//		assertEquals("El actor no puede ser nulo.", ex.getMessage());
//		assertEquals("Los nombres no pueden ser vacíos.", ex.getMessage());		
//		verify(ar, never()).save(any(Actor.class));
//	}
	
	@Test
	@DisplayName("Modificar actor")
	public void testModificar() throws ItemNotFoundException, InvalidDataException {		
		var actor = new Actor(10, "Irene", "Mayorino");
		when(ar.findById(10)).thenReturn(Optional.of(actor));			
		actor.setFirstName("Chiara");	
		as.modify(actor);		
		assertEquals("Chiara", actor.getFirstName());
		verify(ar).save(any(Actor.class)); 		
	}
	
	@Test
	@DisplayName("Modificar actor inexistente")
	public void testModificarInexistente() throws ItemNotFoundException {
		var actor = new Actor(1000, "Fulano", "Fulanes");
		when(ar.findById(actor.getActorId())).thenReturn(Optional.empty());
		var ex = assertThrows(ItemNotFoundException.class, () -> as.modify(actor));
		assertEquals("No existe actor con id: " + actor.getActorId(), ex.getMessage());
		verify(ar).findById(actor.getActorId());
		verify(ar, never()).save(any(Actor.class));
	}
	
	@Test
	@DisplayName("Modificar null")
	public void testModificalNull() {
		var ex = assertThrows(InvalidDataException.class, () -> as.modify(null));
		assertEquals("El actor no puede ser nulo.", ex.getMessage());
		verify(ar, never()).save(any(Actor.class));
	}
	
	@Test
	@DisplayName("Borrar por id")
	public void testDeleteActorById() throws ItemNotFoundException {
		var id = 208;	
		when(ar.findById(id)).thenReturn(Optional.of(mock(Actor.class)));	
		doNothing().when(ar).deleteById(id);	
		as.deleteById(id);	
		verify(ar).findById(id);
		verify(ar).deleteById(id);
	}

	@Test
	@DisplayName("Borrar por id inexistente")
	public void testDeleteActorByIdInexistente() {		
		var id = 208;	
		when(ar.findById(id)).thenReturn(Optional.empty());	
		var ex = assertThrows(ItemNotFoundException.class, () -> as.deleteById(id));
		assertEquals("No existe actor con id: " + id, ex.getMessage());
	}
	
	@Test
	@DisplayName("Borrar")
	public void testDeleteActor() throws InvalidDataException {
		var id = 208;
		when(ar.findById(id)).thenReturn(Optional.of(mock(Actor.class)));
		var actor=ar.findById(id).get();
		doNothing().when(ar).delete(actor);	
		as.delete(actor);		
		verify(ar).delete(actor);
	}	
	
	/* Pruebas de validación */
	@Test
	public void testNombreVacio() {
		Actor a = new Actor(0, "", "Apellido");
		Set<ConstraintViolation<Actor>> violations = validator.validate(a);		
		assertFalse(violations.isEmpty());			
	}
	
	@Test
	public void testNombreInvalido() {
		Actor a = new Actor(0, "nombre", "Apellido", new Timestamp(System.currentTimeMillis()), new ArrayList<>());
		Set<ConstraintViolation<Actor>> violations = validator.validate(a);	
		for (ConstraintViolation<Actor> violation: violations) {
			System.out.println("Property path "+violation.getPropertyPath());
			System.out.println("Message "+violation.getMessage());
			System.out.println("Invalid value "+violation.getInvalidValue());
		}	
		assertFalse(violations.isEmpty());				
		assertTrue(violations.iterator().next().getMessage().contains("debe empezar con mayúscula"));
	}
	
	@Test
	public void testNombreValido() {
		Actor a = new Actor(0, "Nombre", "Apellido", new Timestamp(System.currentTimeMillis()), new ArrayList<>());
		Set<ConstraintViolation<Actor>> violations = validator.validate(a);			
		assertTrue(violations.isEmpty());
	}

}
