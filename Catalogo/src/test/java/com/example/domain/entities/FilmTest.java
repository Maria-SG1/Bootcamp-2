package com.example.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.domain.contracts.repository.ActorRepository;
import com.example.domain.contracts.repository.FilmRepository;
import com.example.domain.service.ActorServiceImpl;
import com.example.domain.service.FilmServiceImpl;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

class FilmTest {
	
	private FilmRepository fr; 
	private FilmServiceImpl fs; 
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	
	@BeforeEach
	void setup() throws Exception {
		fr = mock(FilmRepository.class); 
		fs = new FilmServiceImpl(fr); 			
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}


	@Test
	@DisplayName("Obtener todas películas")
	public void getAll() {		
		when(fr.findAll()).thenReturn(List.of(mock(Film.class)));
		List<Film> films = fs.getAll();
		assertNotNull(films);
		assertEquals(1, films.size());
		verify(fr).findAll(); 
	}
	
	@Test
	@DisplayName("Obtener todas películas vacío")
	public void getAllVacio() {			
		when(fr.findAll()).thenReturn(Collections.emptyList());
		List<Film> films = fs.getAll();
		assertNotNull(films);
		assertEquals(0, films.size());
		verify(fr).findAll(); 
	}
	
	@Test
	@DisplayName("Buscar film por id")
	public void getOne() {		
		var id = 10;	
		when(fr.findById(id)).thenReturn(Optional.of(mock(Film.class)));
		Optional<Film> film = fs.getOne(id);
		assertTrue(film.isPresent());
		verify(fr).findById(id);
	}
	
	@Test 
	@DisplayName("Buscar film por id inexistente")
	public void getOneInexistente() {
		var id = 1000;
		when(fr.findById(id)).thenReturn(Optional.empty());
		Optional<Film> film = fs.getOne(id);
		assertTrue(film.isEmpty());
		verify(fr).findById(id);
	}

	@Test
	@DisplayName("Añadir pelicula")
	public void testAddFilm() throws DuplicateKeyException, InvalidDataException {
		var f = new Film(0, "101 Dálmatas");
		when(fr.save(any(Film.class))).thenReturn(f);
		var addedFilm = fs.add(f);
		assertEquals("101 Dálmatas", addedFilm.getTitle());		
		verify(fr).save(any(Film.class)); 
	}
	
	@Test
	@DisplayName("Añadir clave duplicada")
	public void testAddClaveDuplicada() throws DuplicateKeyException, InvalidDataException {
		var f = new Film(210, "101 Dálmatas");
		System.out.println(f);
		when(fr.findById(210)).thenReturn(Optional.of(new Film(210,"Otra peli")));
		System.out.println(Optional.of(new Film(210,"Otra peli")));
		var ex = assertThrows(DuplicateKeyException.class, ()->fs.add(f));
		assertEquals("Ya existe película con este id.", ex.getMessage());
		verify(fr, never()).save(any(Film.class));
	}
	
	@Test
	@DisplayName("Añadir datos inválidos")
	public void testAddInvalidData() throws DuplicateKeyException, InvalidDataException  {
		var nullex = assertThrows(InvalidDataException.class, ()->fs.add(null));
		assertEquals("El film no puede ser nulo.", nullex.getMessage());
		verify(fr, never()).save(any(Film.class));
	}
	
	@Test
	@DisplayName("Modificar film")
	public void testModificar() throws ItemNotFoundException, InvalidDataException {		
		var f = new Film(10, "ABCDE");
		when(fr.findById(10)).thenReturn(Optional.of(f));			
		f.setTitle("EDCBA");	
		fs.modify(f);		
		assertEquals("EDCBA", f.getTitle());
		verify(fr).save(any(Film.class)); 		
	}
	
	@Test
	@DisplayName("Modificar film inexistente")
	public void testModificarInexistente() throws ItemNotFoundException {
		var f = new Film(1000, "Loquesea");
		when(fr.findById(f.getFilmId())).thenReturn(Optional.empty());
		var ex = assertThrows(ItemNotFoundException.class, () -> fs.modify(f));
		assertEquals("No existe film con el ID " + f.getFilmId(), ex.getMessage());
		verify(fr).findById(f.getFilmId());
		verify(fr, never()).save(any(Film.class));
	}
	
	@Test
	@DisplayName("Modificar null")
	public void testModificalNull() {
		var ex = assertThrows(InvalidDataException.class, () -> fs.modify(null));
		assertEquals("El film no puede ser nulo.", ex.getMessage());
		verify(fr, never()).save(any(Film.class));
	}
	
	@Test
	@DisplayName("Borrar por id")
	public void testDeleteFilmById() throws ItemNotFoundException {
		var id = 208;	
		when(fr.findById(id)).thenReturn(Optional.of(mock(Film.class)));	
		doNothing().when(fr).deleteById(id);	
		fs.deleteById(id);	
		verify(fr).findById(id);
		verify(fr).deleteById(id);
	}

	@Test
	@DisplayName("Borrar por id inexistente")
	public void testDeleteFilmByIdInexistente() {		
		var id = 908;	
		when(fr.findById(id)).thenReturn(Optional.empty());	
		var ex = assertThrows(ItemNotFoundException.class, () -> fs.deleteById(id));
		assertEquals("No existe film con id: " + id, ex.getMessage());
	}
	
	@Test
	@DisplayName("Borrar")
	public void testDeleteActor() throws InvalidDataException {
		var id = 208;
		when(fr.findById(id)).thenReturn(Optional.of(mock(Film.class)));
		var f=fr.findById(id).get();
		doNothing().when(fr).delete(f);	
		fs.delete(f);		
		verify(fr).delete(f);
	}	

}
