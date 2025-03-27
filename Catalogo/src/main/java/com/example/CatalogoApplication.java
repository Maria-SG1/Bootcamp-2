package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domain.contracts.repository.ActorRepository;
import com.example.domain.contracts.repository.FilmRepository;
import com.example.domain.contracts.service.ActorService;
import com.example.domain.entities.Actor;
import com.example.domain.entities.dto.ActorDTO;
import com.example.domain.entities.dto.FilmDTO;
import com.example.domain.entities.dto.FilmShort;
import com.example.exceptions.ItemNotFoundException;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {		
		System.err.println("Applicación arrancada");

	}

}
