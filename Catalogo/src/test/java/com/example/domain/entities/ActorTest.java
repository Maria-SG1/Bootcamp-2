package com.example.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

import com.example.domain.contracts.repository.ActorRepository;
import com.example.domain.service.ActorServiceImpl;
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
		as.getAll();
		verify(ar).findAll(); 
	}
	
	@Test
	public void getOne() {		
		var id = 201;	
		when(ar.findById(id)).thenReturn(Optional.of(mock(Actor.class)));
		as.getOne(id);
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
	
//	@Test
//	public void testModificar() throws ItemNotFoundException, InvalidDataException {		
//		var id = 201;	
//		when(ar.findById(id)).thenReturn(Optional.of(mock(Actor.class)));
//		var a = Optional.of(mock(Actor.class)).get();
//		a.setFirstName("Pepito");
//		var modifiedActor = as.modify(a);
//		assertEquals("Pepito", modifiedActor.getFirstName());
//		verify(ar).save(any(Actor.class)); 
//		
//	}
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
		var thrown = assertThrows(ItemNotFoundException.class, () -> as.deleteById(id));
		assertEquals("No existe actor con id: " + id, thrown.getMessage());
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
