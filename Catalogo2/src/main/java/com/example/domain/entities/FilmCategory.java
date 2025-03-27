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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name="film_category")
@NamedQuery(name="FilmCategory.findAll", query="SELECT f FROM FilmCategory f")
public class FilmCategory  extends AbstractEntity<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@NotNull
	private FilmCategoryPK id;

	@Column(name="last_update", nullable=false)
	@NotNull(message = "La fecha de la última actualización no puede ser nula.")
	@PastOrPresent
	private Timestamp lastUpdate;

	@ManyToOne
	@JoinColumn(name="category_id", nullable=false, insertable=false, updatable=false)
	@NotNull
	private Category category;

	@ManyToOne
	@JoinColumn(name="film_id", nullable=false, insertable=false, updatable=false)
	@NotNull
	private Film film;

	public FilmCategory() {
	}

	public FilmCategory(Film film, Category category) {
		this.film = film;
		this.category = category;
	}

	public FilmCategoryPK getId() {
		return this.id;
	}

	public void setId(FilmCategoryPK id) {
		this.id = id;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}
	
	@PrePersist
	@PreUpdate
	void prePersiste() {
//		System.err.println("prePersiste(): Bug Hibernate");
		if (id == null) {
			setId(new FilmCategoryPK(film.getFilmId(), category.getCategoryId()));
		}
	}

}