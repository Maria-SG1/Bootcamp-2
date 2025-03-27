package com.example.domain.entities.dto;

import com.example.domain.entities.Language;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor
@Schema(name="Idioma")
public class LanguageDTO {
	private int languageId;
	private String name;
	
	public static LanguageDTO from(Language source) {
		return new LanguageDTO(source.getLanguageId(), source.getName());
	}
	
	public static Language from(LanguageDTO source) {
		return new Language(source.getLanguageId(), source.getName());
	}
}
