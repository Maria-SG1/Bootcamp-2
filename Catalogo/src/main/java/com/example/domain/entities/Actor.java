package com.example.domain.entities;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.domain.core.entities.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="actor")
@NamedQuery(name="Actor.findAll", query="SELECT a FROM Actor a")
public class Actor  extends AbstractEntity<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="actor_id", unique=true, nullable=false)
	@JsonProperty("id")
	private int actorId;

	@Column(name="first_name", nullable=false, length=45)
	@NotBlank
	@Size(max = 45, min = 2)
	@Pattern(regexp = "^[A-Z][a-z]+$", message = "El nombre debe empezar con mayúscula y contener solo letras")
	@JsonProperty("firstName")
	private String firstName;

	@Column(name="last_name", nullable=false, length=45)
	@NotBlank
	@Size(max = 45, min = 2)
	@Pattern(regexp = "^[A-Z][a-z]+$", message = "El apellido debe empezar con mayúscula y contener solo letras")
	@JsonProperty("lastName")
	private String lastName;	
	
	@Column(name="last_update", insertable=false, updatable=false, nullable=false)	
	@PastOrPresent
	private Timestamp lastUpdate;
	
//	@OneToMany(mappedBy="actor", fetch = FetchType.EAGER)
	@OneToMany(mappedBy="actor")  
	@Valid
	@JsonIgnore
	private List<FilmActor> filmActors;

	public Actor() {
	}

	public Actor(int actorId, String firstName, String lastName) {
		super();
		this.actorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.filmActors = new ArrayList<>();
	}	
	
	public Actor(int actorId,
			@NotBlank @Size(max = 45, min = 2) @Pattern(regexp = "^[A-Z][a-z]+$", message = "El nombre debe empezar con mayúscula y contener solo letras") String firstName,
			@NotBlank @Size(max = 45, min = 2) @Pattern(regexp = "^[A-Z][a-z]+$", message = "El apellido debe empezar con mayúscula y contener solo letras") String lastName,
			@NotNull(message = "La fecha de la última actualización no puede ser nula.") @PastOrPresent Timestamp lastUpdate,
			@Valid List<FilmActor> filmActors) {
		super();
		this.actorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastUpdate = lastUpdate;
		this.filmActors = filmActors;
	}

	public Actor(int actorId) {
		super();
		this.actorId = actorId;		
	}

	public int getActorId() {
		return this.actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<FilmActor> getFilmActors() {
		return this.filmActors;
	}

	public void setFilmActors(List<FilmActor> filmActors) {
		this.filmActors = filmActors;
	}

	public FilmActor addFilmActor(FilmActor filmActor) {
		getFilmActors().add(filmActor);
		filmActor.setActor(this);

		return filmActor;
	}

	public FilmActor removeFilmActor(FilmActor filmActor) {
		getFilmActors().remove(filmActor);
		filmActor.setActor(null);

		return filmActor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return actorId == other.actorId;
	}

	@Override
	public String toString() {
		return "Actor [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate="
				+ lastUpdate + "]";
	}
	
	public void jubilate() {
		// active a false y fecha de baja = fecha actual 
//		if (this.isActive()) {
//			this.setActive(false);
//			fechaBaja = LocalDate.now();
//		}
			
	}

	public int premios() {
		int count = this.getFilmActors().size();
		int countPremiados = 0;
//		for (FilmActor fa: this.getFilmActors()) {
//			System.out.println(fa.getFilm().getTitle());
//		}
		System.out.println("Actor: "+ this.toString()+"; Número películas: "+ count);
		if (count > 30) {
			System.out.println("Premiado por número de películas > 30.");
			countPremiados ++;
		}
		return countPremiados;
	}

}
