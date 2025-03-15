package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ServicioImpl implements Servicio {
	private Repositorio repositorio;
	
	public ServicioImpl(Repositorio repositorio) {
		this.repositorio=repositorio;
	}

// *
//	public ServicioImpl(@Qualifier("repo1") Repositorio repo) {
//		this.repositorio=repo;
//	}
	
	
//	public ServicioImpl(@Qualifier("verdadero") Repositorio repo) {
//		this.repositorio=repo;
//	}
	
	@Override
	public void guardar() {
		repositorio.guardar();
	}
}
