package com.example.domain.entities.dto;

import com.example.domain.entities.Actor;
import com.example.domain.entities.Film;
import com.example.domain.entities.Language;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FilmDTO {
	private int filmId;
	private String title;
	private String description;
	private int length;
	private Short releaseYear;
	private Language languageVO;
	private Language language2;
	
	public static FilmDTO from(Film source) {
		return new FilmDTO(source.getFilmId(), source.getTitle(), source.getDescription(), 
				source.getLength(), source.getReleaseYear(), source.getLanguageVO(), source.getLanguage2());
	}
	
	public static Film from(FilmDTO source) {
		return new Film(source.getFilmId(), source.getTitle());
	}
}
