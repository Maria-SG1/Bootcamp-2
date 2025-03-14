package com.example.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Dependencias {
	
	@Bean
	Repositorio repositorio(Configuracion config, Registro registro) {
		System.err.println("soy el bean");
		//return new RepositorioMock();
		return new RepositorioImpl(config, registro);
	}	
	
	@Bean	@Primary
	Repositorio repo1(Configuracion config, Registro registro) {
		return new RepositorioImpl(config, registro);
	}
	@Bean  
	public Repositorio repo2() {		
		return new RepositorioMock();		
	}
	
	
// sin c-r  *	
//	@Bean
//	public Repositorio repo1() {		
//		return new RepositorioImpl();
//		
//	}
}
