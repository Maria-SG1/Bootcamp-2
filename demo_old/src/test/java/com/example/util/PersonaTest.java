package com.example.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class PersonaTest {
	@Test
	@Tag("smoke")
	void test() {
		var p = new Persona(1, "Pepe", "Pepes");
		assertNotNull(p);
		assertAll("Constructor",
				()->assertEquals(1, p.id),
				()->assertEquals("Pepe", p.nombre, "nombre"),
				()->assertEquals("Pepes", p.apellido, "apellido")
				);
		assertEquals(1, p.id);
		assertEquals("Pepe", p.nombre);
	}
}
