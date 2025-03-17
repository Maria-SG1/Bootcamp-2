package com.example.domain.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.domain.core.entities.AbstractEntity;

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
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category  extends AbstractEntity<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id", unique=true, nullable=false)
	private int categoryId;

	@Column(name="last_update", nullable=false)
	@NotNull(message = "La fecha de la última actualización no puede ser nula.")
	@PastOrPresent
	private Timestamp lastUpdate;

	@Column(nullable=false, length=25)
	@NotBlank
	@Size(max = 25, min = 3)
	@Pattern(regexp = "^[A-Z]*$", message = "El nombre debe estar en mayúsculas")
	private String name;

	@OneToMany(mappedBy="category")
	@Valid
	private List<FilmCategory> filmCategories;

	public Category() {
	}	

	public Category(int categoryId) {
		super();
		this.categoryId = categoryId;
		this.filmCategories = new ArrayList<>();
	}

	public Category(int categoryId, String name) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.filmCategories = new ArrayList<>();
	}
	
	public Category(int categoryId,
			@NotNull(message = "La fecha de la última actualización no puede ser nula.") @PastOrPresent Timestamp lastUpdate,
			@NotBlank @Size(max = 25, min = 3) @Pattern(regexp = "^[A-Z]*$", message = "El nombre debe estar en mayúsculas") String name) {
		super();
		this.categoryId = categoryId;
		this.lastUpdate = lastUpdate;
		this.name = name;
	}

	public int getCategoryId() {
		return this.categoryId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FilmCategory> getFilmCategories() {
		return this.filmCategories;
	}

	public void setFilmCategories(List<FilmCategory> filmCategories) {
		this.filmCategories = filmCategories;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return categoryId == other.categoryId;
	}	

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", lastUpdate=" + lastUpdate + ", name=" + name + "]";
	}

	public FilmCategory addFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().add(filmCategory);
		filmCategory.setCategory(this);

		return filmCategory;
	}

	public FilmCategory removeFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().remove(filmCategory);
		filmCategory.setCategory(null);

		return filmCategory;
	}

}