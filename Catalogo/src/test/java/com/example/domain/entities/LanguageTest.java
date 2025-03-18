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

import com.example.domain.contracts.repository.LanguageRepository;
import com.example.domain.contracts.service.LanguageService;
import com.example.domain.service.LanguageServiceImpl;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

class LanguageTest {
	private Validator validator;
	private LanguageRepository lr;
	private LanguageService ls;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		lr = mock(LanguageRepository.class);
		ls = new LanguageServiceImpl(lr);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Obtener todos")
	public void getAll() {
		when(lr.findAll()).thenReturn(List.of(mock(Language.class)));
		List<Language> languages = ls.getAll();
		assertNotNull(languages);
		assertEquals(1, languages.size());
		verify(lr).findAll(); 
	}
	
	@Test
	@DisplayName("Obtener todos idiomas vacío")
	public void getAllVacio() {			
		when(lr.findAll()).thenReturn(Collections.emptyList());
		List<Language> languages = ls.getAll();
		assertNotNull(languages);
		assertEquals(0, languages.size());
		verify(lr).findAll(); 
	}	
	
	@Test
	@DisplayName("Buscar idioma por id")
	public void getOne() {		
		var id = 10;	
		when(lr.findById(id)).thenReturn(Optional.of(mock(Language.class)));
		Optional<Language> lang = ls.getOne(id);
		assertTrue(lang.isPresent());
		verify(lr).findById(id);
	}
	
	@Test 
	@DisplayName("Buscar idioma por id inexistente")
	public void getOneInexistente() {
		var id = 1000;
		when(lr.findById(id)).thenReturn(Optional.empty());
		Optional<Language> lang = ls.getOne(id);
		assertTrue(lang.isEmpty());
		verify(lr).findById(id);
	}
	
	@Test
	@DisplayName("Añadir idioma")
	public void testAddFilm() throws DuplicateKeyException, InvalidDataException {
		var l = new Language(0, "Italiano");
		when(lr.save(any(Language.class))).thenReturn(l);
		var addedLanguage = ls.add(l);
		assertEquals("Italiano", addedLanguage.getName());		
		verify(lr).save(any(Language.class)); 
	}
	
	@Test
	@DisplayName("Añadir clave duplicada")
	public void testAddClaveDuplicada() throws DuplicateKeyException, InvalidDataException {
		var l = new Language(210, "German");		
		when(lr.findById(210)).thenReturn(Optional.of(new Language(210,"Otro")));		
		var ex = assertThrows(DuplicateKeyException.class, ()->ls.add(l));
		assertEquals("Ya existe idioma con este id.", ex.getMessage());
		verify(lr, never()).save(any(Language.class));
	}
	
	@Test
	@DisplayName("Añadir datos inválidos")
	public void testAddInvalidData() throws DuplicateKeyException, InvalidDataException  {
		var nullex = assertThrows(InvalidDataException.class, ()->ls.add(null));
		assertEquals("El idioma no puede ser nulo.", nullex.getMessage());
		verify(lr, never()).save(any(Language.class));
	}
	
	@Test
	@DisplayName("Modificar idioma")
	public void testModificar() throws ItemNotFoundException, InvalidDataException {		
		var l = new Language(10, "ABCDE");
		when(lr.findById(10)).thenReturn(Optional.of(l));			
		l.setName("EDCBA");	
		ls.modify(l);		
		assertEquals("EDCBA", l.getName());
		verify(lr).save(any(Language.class)); 		
	}
	
	@Test
	@DisplayName("Modificar idioma inexistente")
	public void testModificarInexistente() throws ItemNotFoundException {
		var l = new Language(1000, "Nuevo");
		when(lr.findById(l.getLanguageId())).thenReturn(Optional.empty());
		var ex = assertThrows(ItemNotFoundException.class, () -> ls.modify(l));
		assertEquals("No existe idioma con este ID." + l.getLanguageId(), ex.getMessage());
		verify(lr).findById(l.getLanguageId());
		verify(lr, never()).save(any(Language.class));
	}
	
	@Test
	@DisplayName("Modificar null")
	public void testModificalNull() {
		var ex = assertThrows(InvalidDataException.class, () -> ls.modify(null));
		assertEquals("El idioma no puede ser nulo.", ex.getMessage());
		verify(lr, never()).save(any(Language.class));
	}
	
	@Test
	@DisplayName("Borrar por id")
	public void testDeleteLanguageById() throws ItemNotFoundException {
		var id = 208;	
		when(lr.findById(id)).thenReturn(Optional.of(mock(Language.class)));	
		doNothing().when(lr).deleteById(id);	
		ls.deleteById(id);	
		verify(lr).findById(id);
		verify(lr).deleteById(id);
	}

	@Test
	@DisplayName("Borrar por id inexistente")
	public void testDeleteLanguageByIdInexistente() {		
		var id = 908;	
		when(lr.findById(id)).thenReturn(Optional.empty());	
		var ex = assertThrows(ItemNotFoundException.class, () -> ls.deleteById(id));
		assertEquals("No existe categoría con ID: " + id, ex.getMessage());
	}
	
	@Test
	@DisplayName("Borrar")
	public void testDeleteLanguage() throws InvalidDataException {
		var id = 208;
		when(lr.findById(id)).thenReturn(Optional.of(mock(Language.class)));
		var l = lr.findById(id).get();
		doNothing().when(lr).delete(l);	
		ls.delete(l);		
		verify(lr).delete(l);
	}	

}
