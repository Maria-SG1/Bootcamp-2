package com.example.domain.entities.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.domain.entities.Actor;
import com.example.domain.entities.Film;
import com.example.domain.entities.FilmActor;
import com.example.domain.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name="Película", description="Datos de la película")
public class FilmDTO {
	@JsonProperty("id")
	private int filmId;
	@NotBlank
	@Size(min=2, max=128)
	@Schema(description="Título de la película", example="Titanic")
	private String title;
	private String description;
	private int length;
	@NotNull
	@Min(1920)
	@Max(2030)
	private Short releaseYear;
	
	private byte rentalDuration;
	private BigDecimal rentalRate;
	private BigDecimal replacementCost;
	
	@JsonProperty("idioma_traduccion")
	private Language languageVO;
	@JsonProperty("idioma_original")
	private Language language2;	

	private List<Integer> actors = new ArrayList<>();
	private List<Integer> categories = new ArrayList<>();	

	
	public static Film from(FilmDTO source) {
		Film film = new Film(
				source.getFilmId(), 
				source.getDescription(), 
				source.getLength(), 
				source.getReleaseYear(), 
				source.getRentalDuration(), 
				source.getRentalRate(), 
				source.getReplacementCost(), 
				source.getTitle(), 
				source.getLanguageVO(),  
				source.getLanguage2()
				);
		
//		if (source.getActors() != null) {
//	        source.getActors().forEach(film::addActor);
//	    }
//		if (source.getCategories() != null) {
//	        source.getCategories().forEach(film::addCategory);
//	    }
		
		source.getActors().stream().forEach(item -> film.addActor(item));
		source.getCategories().stream().forEach(item -> film.addCategory(item));
	    return film;
	}
	
	public static FilmDTO from(Film source) {
	return new FilmDTO(
			source.getFilmId(), 
			source.getTitle(),
			source.getDescription(),
			source.getLength(),			
			source.getReleaseYear(),
			source.getRentalDuration(),
			source.getRentalRate(),
			source.getReplacementCost(),			
			source.getLanguageVO(),  
			source.getLanguage(),
			source.getActors().stream().map(item -> item.getActorId())
				.collect(Collectors.toList()),
			source.getCategories().stream().map(item -> item.getCategoryId())
				.collect(Collectors.toList())
			);
	}
	

}
