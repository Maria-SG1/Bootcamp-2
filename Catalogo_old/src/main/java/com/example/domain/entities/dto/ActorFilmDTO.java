package com.example.domain.entities.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ActorFilmDTO {
	private String fullName;
	private List<String> films;
}

