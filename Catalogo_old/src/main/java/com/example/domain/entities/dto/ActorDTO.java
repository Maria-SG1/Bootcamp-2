package com.example.domain.entities.dto;

import com.example.domain.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(name="Actor", description="Datos del actor")
public class ActorDTO {
	@JsonProperty("id")
	private int actorId;
	@NotBlank
	@Size(min=2, max=45)
	@Schema(description="Nombre del actor", example="Penelope")
	@JsonProperty("nombre")
	private String firstName;
	@NotBlank
	@Size(min=2, max=45)
	@Schema(description="Apellidos del actor", example="Grillo")
	@JsonProperty("apellido")
	private String lastName;
	
	
	public static ActorDTO from(Actor source) {
		return new ActorDTO(source.getActorId(), source.getFirstName(), source.getLastName());
	}
	
	public static Actor from(ActorDTO source) {
		return new Actor(source.getActorId(), source.getFirstName(), source.getLastName());
	}
	
//	public static ActorDTO from(Actor source) {
//		String fullName = source.getFirstName()+" "+source.getLastName();
//		return new ActorDTO(source.getActorId(), fullName);
//	}
//	
//	public static Actor from(ActorDTO source) {
//		String[] names = source.getFullName().split(" ", 2);
//		return new Actor(source.getActorId(), names[0], names [1]);
//	}
	
	
}
