package com.example.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.test.utils.Smoke;

class CalculadoraTest {

	Calculadora calc;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	@DisplayName("Suma")
	class Suma {
		@Nested
		@DisplayName("Casos validos")
		class OK {
			@Test
			@DisplayName("Suma 2 enteros")
//			@Tag("Smoke")
			@Smoke
			void testSuma() {
//				var calc = new Calculadora();
				var actual = calc.suma(2, 3);
				assertEquals(5, actual);		
			}
			
			@Test
			@DisplayName("Suma 2 reales")
			void testSuma2() {
//				var calc = new Calculadora();
				var actual = calc.suma(0.1, 0.2);
//				assertEquals(0.1, calc.suma(1, -0.9));
				assertEquals(0.3, actual);		
			}
			
			@Test
			@DisplayName("Suma 2 reales: resta")
			void testSuma3() {
//				var calc = new Calculadora();	
				assertEquals(0.1, calc.suma(1, -0.9));
			}			
			
			@DisplayName("Test Parametrizado")
			@ParameterizedTest(name= "{0}+{1}={2}")
			@CsvSource({"1,2,3","2,-1,1", "-1,2,1", "-2,-1,-3", "0,0,0", "0.1,0.2,0.3"})
			void testParametrizado(double operando1, double operando2, double expected) {
//				var calc = new Calculadora();
				var actual = calc.suma(operando1, operando2);				
				assertEquals(expected, actual);		
			}
		}
		@Nested
		@DisplayName("Casos invalidos")
//		@Disabled
		class NotOK {
			@Test
			@DisplayName("Suma 2 enteros grandes")
			@Disabled
			void testSumaInt() {
				var calc = new Calculadora();
				var actual = calc.suma(Integer.MAX_VALUE, 1);
				assertEquals(5, actual);
//				assertTrue(1 / 0 > 0);  // error no controlado
				assertTrue(1.0 / 0 > 0); // failure
				assertEquals(5, 1.0 / 0);
				assertTrue(actual>0);		
			}	
		}
	}
	
	
	@Nested
	@DisplayName("Divide")
	class Divide {
		@Nested
		@DisplayName("Casos validos")
		class OK {
			@Test
			@DisplayName("División 2 enteros")
			void testDivide() {
				var calc = new Calculadora();
				//var actual = calc.divide(1, 2);  // division entera
				var actual = calc.divide(1.0, 2);
//				var actual = calc.divide(1, 0);
				assertEquals(0.5, actual);		
			}
			
			@Test
			@DisplayName("División por 0")
			void testDivide1() {
				var calc = new Calculadora();	
				
				//ArithmeticException: / by zero
//				double actual = calc.divide(1.0, 0);
//				assertEquals(0.0, actual);
			
//				assertThrows(ArithmeticException.class, ()->calc.divide(1, 0));
				var ex = assertThrows(ArithmeticException.class, ()->calc.divide(1.0, 0));
				assertEquals("/ by zero", ex.getMessage());
						
			}
			
			@Test
			@DisplayName("División por 0: try")
			void testDivide3() {
				var calc = new Calculadora();		
				try {
					calc.divide(1.0, 0);
					fail("No se ha lanzado excepcion");
				} catch (ArithmeticException e) {
					assertEquals("/ by zero", e.getMessage());
				}		
						
			}
		}
	}
		
	
	@Nested
	@DisplayName("Suplanta")
	class Suplantaciones {
		@Test
		void suplanta() {
			var calc = mock(Calculadora.class);
			when(calc.suma(anyInt(), anyInt())).thenReturn(3).thenReturn(5);
			var actual = calc.suma(2, 2);
			assertEquals(3, actual);
			assertEquals(5, calc.suma(2, 2));
			assertEquals(5, calc.suma(7,3));
		}
		@Test
		void suplanta2() {
			var calc = mock(Calculadora.class);
			when(calc.suma(anyInt(), anyInt())).thenReturn(4);
			var cl = mock(RepositoryInt.class);  // clase que impl. interface
			doNothing().when(cl).guardar();
			var obj = new Factura(calc, cl);
			var actual = obj.calcTotal(2, 2);
			obj.emitir();
//			assertEquals(4, actual);
//			verify(calc).suma(2, 2);
			verify(cl).guardar();
		}
//		@Test
//		void Integracion() {
//			var obj = new Factura(new Calculadora());
//			var actual = obj.calcTotal(2, 2);
//			assertEquals(4, actual);
//		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
