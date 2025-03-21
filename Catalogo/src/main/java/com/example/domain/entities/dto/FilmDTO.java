package com.example.domain.entities.dto;

import com.example.domain.entities.Actor;
import com.example.domain.entities.Film;
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
	private int releaseYear;
	@JsonProperty("idioma_original")
	private Language languageVO;
	@JsonProperty("idioma_traduccion")
	private Language language2;
	
	public static FilmDTO from(Film source) {
		return new FilmDTO(source.getFilmId(), source.getTitle(), source.getDescription(), 
				source.getLength(), source.getReleaseYear(), source.getLanguageVO(), source.getLanguage2());
	}
	
	public static Film from(FilmDTO source) {
		return new Film(source.getFilmId(), source.getTitle());
	}
}
