package com.example.domain.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import com.example.domain.core.entities.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name="film_actor")
@NamedQuery(name="FilmActor.findAll", query="SELECT f FROM FilmActor f")
public class FilmActor  extends AbstractEntity<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FilmActorPK id;

	@Column(name="last_update", nullable=false)
	@NotNull(message = "La fecha de la última actualización no puede ser nula.")
	@PastOrPresent
	private Timestamp lastUpdate;

	@ManyToOne
	@JoinColumn(name="actor_id", nullable=false, insertable=false, updatable=false)
	@NotNull
	private Actor actor;

	@ManyToOne
	@JoinColumn(name="film_id", nullable=false, insertable=false, updatable=false)
	@NotNull
	private Film film;

	public FilmActor() {
	}

	public FilmActorPK getId() {
		return this.id;
	}

	public void setId(FilmActorPK id) {
		this.id = id;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Actor getActor() {
		return this.actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

}