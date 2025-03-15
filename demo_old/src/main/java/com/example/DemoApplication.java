package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import com.example.domain.contracts.repositories.ActoresRepository;
import com.example.domain.contracts.services.ActorService;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.err.println("Applicación arrancada");		
		ejemplosDePrueba();
	}
	
	@Autowired
	private ActoresRepository dao;	
	
	@Autowired
	private ActorService srv;	
	
	private void ejemplosDePrueba() {
//		var actor = new Actor(0, "Pepito", "Grillo");
		
//		var item = dao.findById(201);  // Optional
//		if (item.isPresent()) {
//			var actor = item.get();
//			actor.setFirstName("Pepe");
//			actor.setLastName(actor.getLastName().toUpperCase());
//			dao.save(actor);
//			//System.err.println("Actor: "+item);
//		} else {
//			System.err.println("No encontrado");
//		}
//		dao.findAll().forEach(System.err::println);
//		dao.deleteById(202);
//		dao.deleteById(203);
//		dao.findAll().forEach(System.err::println);
		
//		dao.findTop10ByFirstNameStartingWithOrderByLastNameDesc("P").forEach(System.err::println);
//		dao.findTop10ByFirstNameStartingWith("P", Sort.by("FirstName").ascending()).forEach(System.err::println);
//		dao.findByActorIdGreaterThan(190).forEach(System.err::println);
//		dao.findNovedadesJPQL(195).forEach(System.err::println);
//		dao.findNovedadesSQL(185).forEach(System.err::println);
//		
//		dao.findAll((root, query, builder)->builder.greaterThan(root.get("actorId"),193)).forEach(System.err::println);
//		dao.findAll((root, query, builder)->builder.lessThanOrEqualTo(root.get("actorId"),5)).forEach(System.err::println);
		
//		srv.getAll().forEach(System.err::println);
		var item = srv.getOne(1);
		
		if (item.isPresent()) {
			var actor = item.get();
			System.err.println(item+" \n in peliculas");
			actor.getFilmActors().forEach(fa->System.err.println(fa.getFilm().getTitle()));
		} else {
			System.err.println("No encontrado");
		}
	}
	


}
