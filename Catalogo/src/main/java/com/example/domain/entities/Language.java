package com.example.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="language")
@NamedQuery(name="Language.findAll", query="SELECT l FROM Language l")
public class Language  extends AbstractEntity<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="language_id", unique=true, nullable=false)
	private int languageId;

	@Column(nullable=false, length=20)
	@NotBlank
	@Size(min = 2, max = 15)
	@Pattern(regexp = "^[A-Z][a-z]+$", message = "El nombre debe comenzar con mayúscula seguida de letras minúsculas.")
	private String name;

	@OneToMany(mappedBy="languageVO")
	@Valid
	private List<Film> filmsVO;

	@OneToMany(mappedBy="language2")
	@Valid
	private List<Film> films2;	

	public Language() {
	}

	public Language(int languageId) {
		super();
		this.languageId = languageId;
		this.films2 = new ArrayList<>();
		this.filmsVO = new ArrayList<>();
	}
	
	public Language(int languageId,
			@NotBlank @Size(min = 2, max = 15) @Pattern(regexp = "^[A-Z][a-z]+$", message = "El nombre debe comenzar con mayúscula seguida de letras minúsculas.") String name) {
		super();
		this.languageId = languageId;
		this.name = name;
	}

	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Film> getFilmsVO() {
		return this.filmsVO;
	}

	public void setFilmsVO(List<Film> filmsVO) {
		this.filmsVO = filmsVO;
	}

	public Film addFilmsVO(Film filmsVO) {
		getFilmsVO().add(filmsVO);
		filmsVO.setLanguageVO(this);

		return filmsVO;
	}

	public Film removeFilmsVO(Film filmsVO) {
		getFilmsVO().remove(filmsVO);
		filmsVO.setLanguageVO(null);

		return filmsVO;
	}

	public List<Film> getFilms2() {
		return this.films2;
	}

	public void setFilms2(List<Film> films2) {
		this.films2 = films2;
	}

	public Film addFilms2(Film films2) {
		getFilms2().add(films2);
		films2.setLanguage2(this);

		return films2;
	}

	public Film removeFilms2(Film films2) {
		getFilms2().remove(films2);
		films2.setLanguage2(null);

		return films2;
	}

	@Override
	public String toString() {
		return "Language [languageId=" + languageId + ", name=" + name + "]";
	}

}