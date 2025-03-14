package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

//@Repository
//@Qualifier("verdadero")
//@Primary
public class RepositorioImpl implements Repositorio {
//	private Configuracion config;
//	private Registro registro;
	
	public RepositorioImpl(Configuracion config, Registro registro) {
//		this.config = config;
//		this.registro = registro;
	}
	
	@Override
	public void guardar() {
		System.err.println("Guardado");
	}
	
}
