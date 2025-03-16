package com.example.domain.entities.dto;

import java.util.List;

import com.example.domain.entities.Actor;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ActorDTO {
	private int actorId;
//	private String fullName;
	private String firstName;
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
