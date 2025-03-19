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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.example.domain.contracts.repository.FilmRepository;
import com.example.domain.service.FilmServiceImpl;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class FilmTest {
	private Validator validator;
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
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("Find All")
	class FindAll {
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
	}
	
	@Nested
	@DisplayName("Find By ID")
	class FindOne {
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
	}
	
	@Nested
	@DisplayName("Add Film")
	class Add {
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
	}
	
	@Nested
	@DisplayName("Edit Film")
	class Edit {
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
	}
	
	@Nested
	@DisplayName("Delete Actor")
	class Delete {
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
		public void testDeleteFilm() throws InvalidDataException {
			var id = 208;
			when(fr.findById(id)).thenReturn(Optional.of(mock(Film.class)));
			var f=fr.findById(id).get();
			doNothing().when(fr).delete(f);	
			fs.delete(f);		
			verify(fr).delete(f);
		}	
	}
	
	@Test
	@DisplayName("Title vacío violación @NotBlank")
	public void testTituloVacio() {
		Film f = new Film(0, " ");
		Set<ConstraintViolation<Film>> violations = validator.validate(f);		
		assertFalse(violations.isEmpty());			
	}
	
	@Test
	@DisplayName("Length")
	public void testLength() {
//		Film f = new Film(0, " ", new Timestamp(System.currentTimeMillis()), 300, "Titulo");
		Film f = new Film(0, " ");
		f.setLength(300);
		Set<ConstraintViolation<Film>> violations = validator.validate(f);
		assertFalse(violations.isEmpty());	
	}	
	
	@ParameterizedTest
	@CsvSource({ "15, 0", "210, 0", "100, 0", "14, 1", "211, 1"})
	public void testLength(int length, int numViolations) {
		Film f = new Film();
		f.setLength(length);
		Set<ConstraintViolation<Film>> violations = validator.validateProperty(f, "length");
		assertEquals(numViolations, violations.size());
	}
	
	@ParameterizedTest
	@CsvSource({ "1920, 0", "2030, 0", "1919, 1", "2031, 1", "null, 1"})
	public void testReleaseYear(String releaseYear, int numViolations) {
		Film f = new Film();
		if (!"null".equals(releaseYear)) {
			f.setReleaseYear(Short.valueOf(releaseYear));
		} else {
			f.setReleaseYear(null);
		}
		Set<ConstraintViolation<Film>> violations = validator.validateProperty(f, "releaseYear");
		assertEquals(numViolations, violations.size());
	}
	
	@ParameterizedTest
	@CsvSource({ "1, 0", "10, 0", "0, 1", "11, 1", "-1, 1"})
	public void testRentalDuration(String rentalDuration, int numViolations) {
		Film f = new Film();
		f.setRentalDuration(Byte.valueOf(rentalDuration));
		Set<ConstraintViolation<Film>> violations = validator.validateProperty(f, "rentalDuration");
		assertEquals(numViolations, violations.size());
	}
	
	@ParameterizedTest
	@CsvSource({ "0.01, 0", "1000.00, 0", "0.00, 1", "1000.01, 1", "null, 1"})
	public void testRentalRate(String rentalRate, int numViolations) {
		Film f = new Film();
		if (!"null".equals(rentalRate)) {
			f.setRentalRate(new BigDecimal(rentalRate));
		} else {
			f.setRentalRate(null);
		}		
		Set<ConstraintViolation<Film>> violations = validator.validateProperty(f, "rentalRate");
		assertEquals(numViolations, violations.size());
	}

	@ParameterizedTest
	@CsvSource({ "0.01, 0", "500.00, 0", "0.00, 1", "null, 1"})
	public void testReplacementCost(String replacementCost, int numViolations) {
		Film f = new Film();
		if (!"null".equals(replacementCost)) {
			f.setReplacementCost(new BigDecimal(replacementCost));
		} else {
			f.setReplacementCost(null);
		}		
		Set<ConstraintViolation<Film>> violations = validator.validateProperty(f, "replacementCost");
		assertEquals(numViolations, violations.size());
	}
	
	private static Stream<Object[]> datos() {
		return Stream.of(
				new Object[] {new Actor(0, "Nombreuno", "Apellidouno"), new Actor(0, "Nombredos", "Apellidodos"), "Nombreuno"},
				new Object[] {new Category(0, "Categoriauno"), new Category(0, "Categoriados"), "Categoriauno"}
				);
	}
	
	@ParameterizedTest
	@MethodSource("datos")
	public void testFilmCollections(Object item1, Object item2, String resultado) {
		Film f = new Film(0, "Abc");
		if (item1 instanceof Actor) {
			List<FilmActor> coleccion = new ArrayList<>(f.getFilmActors());
			coleccion.addAll(Arrays.asList(
					new FilmActor((Actor)item1, new Film(0, "Filmuno")),
					new FilmActor((Actor)item2, new Film(0, "Filmdos"))));
			f.setFilmActors(coleccion);
			assertEquals(resultado, f.getFilmActors().get(0).getActor().getFirstName());
			
		} else if (item1 instanceof Category){
			List<FilmCategory> coleccion = new ArrayList<>(f.getFilmCategories());
			coleccion.addAll(Arrays.asList(
					new FilmCategory((Category)item1, new Film(0, "Filmuno")),
					new FilmCategory((Category)item2, new Film(0, "Filmdos"))));
			f.setFilmCategories(coleccion);
			assertEquals(resultado, f.getFilmCategories().get(0).getCategory().getName());
		}
	}
	
	@Test
	public void testDeleteFilmConActores() throws ItemNotFoundException, InvalidDataException {
		var film = mock(Film.class);
		var filmActor = mock(FilmActor.class);
		List<FilmActor> filmActors = new ArrayList<>();
		filmActors.add(filmActor);
		when(film.getFilmActors()).thenReturn(filmActors);
		fs.delete(film);
		verify(fr).delete(film);	
	}
	

}
