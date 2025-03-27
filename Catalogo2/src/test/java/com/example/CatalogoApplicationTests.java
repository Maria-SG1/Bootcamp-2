package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import com.example.domain.contracts.repository.ActorRepository;
import com.example.domain.entities.Actor;
import com.example.domain.service.ActorServiceImpl;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.ItemNotFoundException;

@SpringBootTest
class CatalogoApplicationTests {
//	@Autowired
//	private ActorServiceImpl asi;
//	
//	@Test
//	void test1() throws DuplicateKeyException, InvalidDataException {
//		Actor a = new Actor(0,"Ana","Garcia");
//		Actor a1 = new Actor(0,"Ana1","Garcia");
//		asi.add(a1);
//		assertEquals("Irene", asi.add(new Actor(0,"Irene","Mayorino")).getFirstName());
//	}
//	
//	@Test
//	void test2() throws ItemNotFoundException {		
//		Integer id = 208;
//		Actor a = asi.getOne(id).orElse(null);
//		asi.deleteById(id);
//		Actor afterDelete = asi.getOne(id).orElse(null);
//		assertNull(afterDelete);	
//		
//	}
	

}
