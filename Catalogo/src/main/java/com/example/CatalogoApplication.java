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

	@Autowired
	private ActorRepository adao;
	
	@Autowired
	private FilmRepository fdao;
	
	@Autowired
	private ActorService srv;	
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {		
		System.err.println("Applicación arrancada");
		
//		var item = adao.findById(2);	
//		
//		System.err.println("Actor s: "+item);
//		
//		if (item.isPresent()) {
//			var actor = item.get();			
//			System.err.println(item+" \n in peliculas");
//			actor.getFilmActors().forEach(fa->System.err.println(fa.getFilm().getTitle()));
//		} else {
//			System.err.println("**No encontrado");
//		}
//		
//		fdao.findByLengthLessThan(100).forEach(f->System.out.println(f.getTitle()+" - "+f.getLength()));
//		fdao.findByTitleStartingWith("FR").forEach(f->System.out.println(f.getTitle()));
//		
//		adao.findAll().forEach(o -> System.err.println(ActorDTO.from(o)));	
//		fdao.findAll().forEach(o -> System.err.println(FilmDTO.from(o)));
//		
//		adao.findByActorIdGreaterThan(200, ActorDTO.class).forEach(System.err::println);
//		
//		fdao.findByFilmIdGreaterThan(990, FilmDTO.class).forEach(System.err::println);		
//		fdao.findByFilmIdGreaterThan(990, FilmShort.class).forEach(o -> System.err.println(o.getId() + " " + o.getTitle() + " " + o.getLanguages()));
//			
//		// Validation
//		var actor = new Actor(0, null, "apellido");
//		if(actor.isValid())
//			 adao.save(actor);
//		 else {
//			System.err.println(actor.getErrorsMessage());
//		}
//		
//		// premios()
//		int totalPremiados = 0;
//		for (Actor a: adao.findAll()) {
//			Actor act = adao.findById(a.getActorId()).orElse(null);
//			if (act != null) 
//				totalPremiados += act.premios();
//		}
//		System.err.println("Número total de premiados: " + totalPremiados);
//		
//		 
//		System.err.println("Actores con películas from DTO");
//		for (Actor a: adao.findAll()) {
//			Actor act = adao.findById(a.getActorId()).orElse(null);
//			if (act != null) {
//				if (act.getActorId()<10)
//					System.out.println(srv.getActorsFilms(a.getActorId()));
//			}
//				
//		}
//		
//		
//		adao.findAll().stream().filter(act-> {
////			return act.getFirstName().equals("LAURA");
//			return act.getActorId()>190;
//		}).forEach(act->{
//			Actor a = adao.findById(act.getActorId()).orElse(null);
//			if (a!=null) {
//				try {
//					System.err.println(srv.getActorsFilms(a.getActorId()));
//				} catch (ItemNotFoundException e) {					
//					e.printStackTrace();
//				}
//			}
//		});
		
		
		

	}

}
