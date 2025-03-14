package com.example.ioc;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

//@Repository
//@Qualifier("falso")  
//@Primary
public class RepositorioMock implements Repositorio {
	@Override
	public void guardar() {
		System.err.println("Guardado falso");
	}
}
