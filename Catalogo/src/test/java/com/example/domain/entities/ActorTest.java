package com.example.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domain.contracts.repository.ActorRepository;
import com.example.domain.service.ActorServiceImpl;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

class ActorTest {
	
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
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	public void getAll() {		
		when(ar.findAll()).thenReturn(List.of(mock(Actor.class)));
		List<Actor> actores = as.getAll();
		assertNotNull(actores);
		assertEquals(1, actores.size());
		verify(ar).findAll(); 
	}
	
	@Test
	public void getAllVacio() {			
		when(ar.findAll()).thenReturn(Collections.emptyList());
		List<Actor> actores = as.getAll();
		assertNotNull(actores);
		assertEquals(0, actores.size());
		verify(ar).findAll(); 
	}
	
	@Test
	public void getOne() {		
		var id = 201;	
		when(ar.findById(id)).thenReturn(Optional.of(mock(Actor.class)));
		Optional<Actor> actor = as.getOne(id);
		assertTrue(actor.isPresent());
		verify(ar).findById(id);
	}

	@Test
	public void testAddActor() throws DuplicateKeyException, InvalidDataException {
		var actor = new Actor(0, "Irene", "Mayorino");
		when(ar.save(any(Actor.class))).thenReturn(actor);
		var addedActor = as.add(actor);
		assertEquals("Irene", addedActor.getFirstName());		
		verify(ar).save(any(Actor.class)); 
	}
	
	@Test
	public void testAddClaveDuplicada() throws DuplicateKeyException, InvalidDataException {
		var actor = new Actor(10, "Irene", "Mayorino");
//		when(ar.findById(10)).thenReturn(Optional.of(ar.findById(10).get()));
		when(ar.findById(10)).thenReturn(Optional.of(new Actor(10,"Pepito","Perez")));
		var ex = assertThrows(DuplicateKeyException.class, ()->as.add(actor));
		assertEquals("Ya existe actor con este id.", ex.getMessage());
		verify(ar, never()).save(any(Actor.class));
	}
	
	@Test
	public void testAddInvalidData() throws DuplicateKeyException, InvalidDataException  {
		var nullex = assertThrows(InvalidDataException.class, ()->as.add(null));
		assertEquals("El actor no puede ser nulo.", nullex.getMessage());
//		var actorFN = new Actor(10, "", "Mayorino");
//		var ex1 = assertThrows(InvalidDataException.class, ()->as.add(actorFN));
//		assertEquals("Los nombres no pueden ser vacíos.", ex1.getMessage());//		
//		var actorLN = new Actor(20, "Ana", "");
//		var ex2 = assertThrows(InvalidDataException.class, ()->as.add(actorLN));
//		assertEquals("Los nombres no pueden ser vacíos.", ex2.getMessage());	
		verify(ar, never()).save(any(Actor.class));
	}
	
	@Test
	public void testModificar() throws ItemNotFoundException, InvalidDataException {		
		var actor = new Actor(10, "Irene", "Mayorino");
		when(ar.findById(10)).thenReturn(Optional.of(actor));			
		actor.setFirstName("Chiara");		
//		when(as.modify(a)).thenReturn(a);
		as.modify(actor);		
		assertEquals("Chiara", actor.getFirstName());
		verify(ar).save(any(Actor.class)); 		
	}
	
	@Test
	public void testDeleteActorById() throws ItemNotFoundException {
		var id = 208;	
		when(ar.findById(id)).thenReturn(Optional.of(mock(Actor.class)));	
		doNothing().when(ar).deleteById(id);	
		as.deleteById(id);	
		verify(ar).findById(id);
		verify(ar).deleteById(id);
	}

	@Test
	public void testDeleteActorByIdInexistente() {		
		var id = 208;	
		when(ar.findById(id)).thenReturn(Optional.empty());	
		var ex = assertThrows(ItemNotFoundException.class, () -> as.deleteById(id));
		assertEquals("No existe actor con id: " + id, ex.getMessage());
	}
	
	@Test
	public void testDeleteActor() throws InvalidDataException {
		var id = 208;
		when(ar.findById(id)).thenReturn(Optional.of(mock(Actor.class)));
		var actor=ar.findById(id).get();
		doNothing().when(ar).delete(actor);	
		as.delete(actor);		
		verify(ar).delete(actor);
	}
	
	

}
