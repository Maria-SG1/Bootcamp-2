package com.example.domain.entities.dto;

import java.util.List;

import com.example.domain.entities.Actor;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ActorFDTO {	
		private int actorId;
		private String firstName;
		private String lastName;	
		private List<String> films;				
				
		public static ActorFDTO from(Actor source, List<String> films) {
			return new ActorFDTO(source.getActorId(), source.getFirstName(), source.getLastName(), films);
		}		
		
		public static Actor from(ActorFDTO source) {
			return new Actor(source.getActorId(), source.getFirstName(), source.getLastName());
		}
}
