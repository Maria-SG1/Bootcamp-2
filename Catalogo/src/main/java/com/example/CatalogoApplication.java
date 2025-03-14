package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domain.contracts.repository.ActorRepository;
import com.example.domain.contracts.repository.FilmRepository;
import com.example.domain.contracts.service.ActorService;

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
		
		var item = adao.findById(2);	
		
		System.err.println("Actor s: "+item);
		
		if (item.isPresent()) {
			var actor = item.get();
			System.err.println(item+" \n in peliculas");
			actor.getFilmActors().forEach(fa->System.err.println(fa.getFilm().getTitle()));
		} else {
			System.err.println("**No encontrado");
		}
		
		fdao.findByLengthLessThan(100).forEach(f->System.out.println(f.getTitle()+" - "+f.getLength()));
		fdao.findByTitleStartingWith("FR").forEach(f->System.out.println(f.getTitle()));
		
	}

}
