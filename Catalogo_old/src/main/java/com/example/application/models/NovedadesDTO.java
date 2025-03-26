package com.example.application.models;

import java.util.List;

import com.example.domain.entities.Category;
import com.example.domain.entities.Language;
import com.example.domain.entities.dto.ActorDTO;
import com.example.domain.entities.dto.FilmShortDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class NovedadesDTO {
	private List<FilmShortDTO> films;
	private List<ActorDTO> actors;
	private List<Category> categories;
	private List<Language> languages;
}
