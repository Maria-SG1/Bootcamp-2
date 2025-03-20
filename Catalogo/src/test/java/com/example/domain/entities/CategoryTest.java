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

import com.example.domain.contracts.repository.CategoryRepository;
import com.example.domain.contracts.service.CategoryService;
import com.example.domain.service.CategoryServiceImpl;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class CategoryTest {
	private Validator validator;
	private CategoryRepository cr;
	private CategoryService cs;	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		cr = mock(CategoryRepository.class);
		cs = new CategoryServiceImpl(cr);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Obtener todas")
	public void getAll() {
		when(cr.findAll()).thenReturn(List.of(mock(Category.class)));
		List<Category> categories = cs.getAll();
		assertNotNull(categories);
		assertEquals(1, categories.size());
		verify(cr).findAll(); 
	}
	
	@Test
	@DisplayName("Obtener todas categorías vacío")
	public void getAllVacio() {			
		when(cr.findAll()).thenReturn(Collections.emptyList());
		List<Category> categories = cs.getAll();
		assertNotNull(categories);
		assertEquals(0, categories.size());
		verify(cr).findAll(); 
	}		
	
	@Test
	@DisplayName("Buscar categoria por id")
	public void getOne() {		
		var id = 10;	
		when(cr.findById(id)).thenReturn(Optional.of(mock(Category.class)));
		Optional<Category> cat = cs.getOne(id);
		assertTrue(cat.isPresent());
		verify(cr).findById(id);
	}
	
	@Test 
	@DisplayName("Buscar categoria por id inexistente")
	public void getOneInexistente() {
		var id = 1000;
		when(cr.findById(id)).thenReturn(Optional.empty());
		Optional<Category> cat = cs.getOne(id);
		assertTrue(cat.isEmpty());
		verify(cr).findById(id);
	}
	
	@Test
	@DisplayName("Añadir categoría")
	public void testAddFilm() throws DuplicateKeyException, InvalidDataException {
		var c = new Category(0, "Aventura");
		when(cr.save(any(Category.class))).thenReturn(c);
		var addedCategory = cs.add(c);
		assertEquals("Aventura", addedCategory.getName());		
		verify(cr).save(any(Category.class)); 
	}
	
	@Test
	@DisplayName("Añadir clave duplicada")
	public void testAddClaveDuplicada() throws DuplicateKeyException, InvalidDataException {
		var c = new Category(210, "Documental");		
		when(cr.findById(210)).thenReturn(Optional.of(new Category(210,"Otra categoria")));		
		var ex = assertThrows(DuplicateKeyException.class, ()->cs.add(c));
		assertEquals("Ya existe categoría con este id.", ex.getMessage());
		verify(cr, never()).save(any(Category.class));
	}
	
	@Test
	@DisplayName("Añadir datos inválidos")
	public void testAddInvalidData() throws DuplicateKeyException, InvalidDataException  {
		var nullex = assertThrows(InvalidDataException.class, ()->cs.add(null));
		assertEquals("La categoría no puede ser nula.", nullex.getMessage());
		verify(cr, never()).save(any(Category.class));
	}
	
	@Test
	@DisplayName("Modificar categoría")
	public void testModificar() throws ItemNotFoundException, InvalidDataException {		
		var c = new Category(10, "ABCDE");
		when(cr.findById(10)).thenReturn(Optional.of(c));			
		c.setName("EDCBA");	
		cs.modify(c);		
		assertEquals("EDCBA", c.getName());
		verify(cr).save(any(Category.class)); 		
	}
	
	@Test
	@DisplayName("Modificar categoría inexistente")
	public void testModificarInexistente() throws ItemNotFoundException, InvalidDataException {
		var c = new Category(1000, "Loquesea");
		when(cr.findById(c.getCategoryId())).thenReturn(Optional.empty());
		var ex = assertThrows(ItemNotFoundException.class, () -> cs.modify(c));
		assertEquals("No existe categoría con este ID." + c.getCategoryId(), ex.getMessage());
		verify(cr).findById(c.getCategoryId());
		verify(cr, never()).save(any(Category.class));	
	}
	
	@Test
	@DisplayName("Modificar null")
	public void testModificalNull() {
		var ex = assertThrows(InvalidDataException.class, () -> cs.modify(null));
		assertEquals("La categoría no puede ser nula.", ex.getMessage());
		verify(cr, never()).save(any(Category.class));
	}
	
	@Test
	@DisplayName("Borrar por id")
	public void testDeleteCategoryById() throws ItemNotFoundException {
		var id = 208;	
		when(cr.findById(id)).thenReturn(Optional.of(mock(Category.class)));	
		doNothing().when(cr).deleteById(id);	
		cs.deleteById(id);	
		verify(cr).findById(id);
		verify(cr).deleteById(id);
	}

	@Test
	@DisplayName("Borrar por id inexistente")
	public void testDeleteCategoryByIdInexistente() {		
		var id = 908;	
		when(cr.findById(id)).thenReturn(Optional.empty());	
		var ex = assertThrows(ItemNotFoundException.class, () -> cs.deleteById(id));
		assertEquals("No existe categoría con ID:" + id, ex.getMessage());
	}
	
	@Test
	@DisplayName("Borrar")
	public void testDeleteCategory() throws InvalidDataException {
		var id = 208;
		when(cr.findById(id)).thenReturn(Optional.of(mock(Category.class)));
		var c = cr.findById(id).get();
		doNothing().when(cr).delete(c);	
		cs.delete(c);		
		verify(cr).delete(c);
	}	
	
	@Test
	@DisplayName("Nombre vacío violación @NotBlank")
	public void testNombreVacio() {
		Category c = new Category(0, " ");
		Set<ConstraintViolation<Category>> violations = validator.validate(c);		
		assertFalse(violations.isEmpty());			
	}
	
	@Test
	@DisplayName("Nombre inválido violación @Pattern")
	public void testNombreInvalido() {
		Category c = new Category(0, new Timestamp(System.currentTimeMillis()), "nombre");
		Set<ConstraintViolation<Category>> violations = validator.validate(c);		
		assertFalse(violations.isEmpty());				
		assertTrue(violations.iterator().next().getMessage().contains("debe estar en mayúsculas"));
	}
	
	@Test
	@DisplayName("Nombre inválido > 25 violación @Size")
	public void testNombreInvalidoSizeMas25() {
		Category c = new Category(0, new Timestamp(System.currentTimeMillis()), "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Set<ConstraintViolation<Category>> violations = validator.validate(c);	
		System.out.println("> 25");
		System.out.println(violations.size());
		for (ConstraintViolation<Category> violation: violations) {
			System.out.println("Property path "+violation.getPropertyPath());
			System.out.println("Message "+violation.getMessage());
			System.out.println("Invalid value "+violation.getInvalidValue());
		}	
		assertFalse(violations.isEmpty());				
		assertTrue(violations.iterator().next().getMessage().contains("between 3 and 25"));
	}
	
	@Test
	@DisplayName("Nombre inválido < 3 violación @Size")
	public void testNombreInvalidoSizeMenos3() {
		Category c = new Category(0, new Timestamp(System.currentTimeMillis()), "AB");
		Set<ConstraintViolation<Category>> violations = validator.validate(c);	
		System.out.println("< 3");
		System.out.println(violations.size());
		for (ConstraintViolation<Category> violation: violations) {
			System.out.println("Property path "+violation.getPropertyPath());
			System.out.println("Message "+violation.getMessage());
			System.out.println("Invalid value "+violation.getInvalidValue());
		}	
		assertFalse(violations.isEmpty());				
		assertTrue(violations.iterator().next().getMessage().contains("between 3 and 25"));
	}	
	
	@Test
	@DisplayName("Nombre válido")
	public void testNombreValido() {
		Category c = new Category(0, new Timestamp(System.currentTimeMillis()), "ABCD");
		Set<ConstraintViolation<Category>> violations = validator.validate(c);			
		assertTrue(violations.isEmpty());
	}
	
	@Test
	@DisplayName("Violation @PastOrPresent")
	public void testLastUpdateFutureDate() {
		Category c = new Category(0, new Timestamp(System.currentTimeMillis()+10000), "Nombre");
		Set<ConstraintViolation<Category>> violations = validator.validate(c);	
		assertFalse(violations.isEmpty());	
	}
	
//	@Test
//	@DisplayName("Violation @NotNull")
//	public void testLastUpdateNullDate() {
//		Category c = new Category(0, null, "Nombre");
//		Set<ConstraintViolation<Category>> violations = validator.validate(c);	
//		assertFalse(violations.isEmpty());	
//	}

}
