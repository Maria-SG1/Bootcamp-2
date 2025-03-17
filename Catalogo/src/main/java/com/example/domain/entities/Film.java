package com.example.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.domain.core.entities.AbstractEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="film")
@NamedQuery(name="Film.findAll", query="SELECT f FROM Film f")
public class Film  extends AbstractEntity<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="film_id", unique=true, nullable=false)
	private int filmId;

	@Lob
	@Size(max = 255)
	private String description;

	@Column(name="last_update", nullable=false)
	@NotNull
	@PastOrPresent
	private Timestamp lastUpdate;

	@Column(name = "length")
	@Min(15)
	@Max(210)
	private int length;

	@Column(length=1)
	@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$", message = "Rating inválido. Valores permitidos: G, PG, PG-13, R, NC-17.")
	private String rating;

	@Column(name="release_year")
	@NotNull
	@Min(1920)
	@Max(2030)
	private Short releaseYear;

	@Column(name="rental_duration", nullable=false)
	@NotNull
	@Min(1)
	@Max(10)
	private byte rentalDuration;

	@Column(name="rental_rate", nullable=false, precision=10, scale=2)
	@NotNull
	@DecimalMin("0.01")
	@DecimalMax("1000.00")
	private BigDecimal rentalRate;

	@Column(name="replacement_cost", nullable=false, precision=10, scale=2)
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal replacementCost;

	@Column(nullable=false, length=128)
	@NotBlank
	@Size(max = 128, min = 2)
	private String title;

	@ManyToOne
	@JoinColumn(name="language_id", nullable=false)
	@NotNull
	private Language languageVO;

	@ManyToOne
	@JoinColumn(name="original_language_id")
	private Language language2;

	@OneToMany(mappedBy="film", 
			cascade = CascadeType.ALL, orphanRemoval = true)
	@Valid
	private List<FilmActor> filmActors;

	@OneToMany(mappedBy="film", 
			cascade = CascadeType.ALL, orphanRemoval = true)
	@Valid
	private List<FilmCategory> filmCategories;	

	public Film() {
	}	

	public Film(int filmId) {
		super();
		this.filmId = filmId;
		this.filmActors = new ArrayList<>();
		this.filmCategories = new ArrayList<>();
	}

	public Film(int filmId, String title) {
		super();
		this.filmId = filmId;
		this.title = title;
		this.filmActors = new ArrayList<>();
		this.filmCategories = new ArrayList<>();
	}	

	public Film(int filmId, @Size(max = 255) String description, @NotNull @PastOrPresent Timestamp lastUpdate,
			@Min(15) @Max(210) int length, @NotBlank @Size(max = 128, min = 2) String title) {
		super();
		this.filmId = filmId;
		this.description = description;
		this.lastUpdate = lastUpdate;
		this.length = length;
		this.title = title;
	}	

	public int getFilmId() {
		return this.filmId;
	}

	public Film(int filmId, @Size(max = 255) String description, @NotNull @PastOrPresent Timestamp lastUpdate,
		@Min(15) @Max(210) int length,
		@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$", message = "Rating inválido. Valores permitidos: G, PG, PG-13, R, NC-17.") String rating,
		@NotNull @Min(1920) @Max(2030) Short releaseYear, @NotNull @Min(1) @Max(10) byte rentalDuration,
		@NotNull @DecimalMin("0.01") @DecimalMax("1000.00") BigDecimal rentalRate,
		@NotNull @DecimalMin("0.01") BigDecimal replacementCost, @NotBlank @Size(max = 128, min = 2) String title,
		@NotNull Language languageVO, Language language2, @Valid List<FilmActor> filmActors,
		@Valid List<FilmCategory> filmCategories) {
	super();
	this.filmId = filmId;
	this.description = description;
	this.lastUpdate = lastUpdate;
	this.length = length;
	this.rating = rating;
	this.releaseYear = releaseYear;
	this.rentalDuration = rentalDuration;
	this.rentalRate = rentalRate;
	this.replacementCost = replacementCost;
	this.title = title;
	this.languageVO = languageVO;
	this.language2 = language2;
	this.filmActors = filmActors;
	this.filmCategories = filmCategories;
}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Short getReleaseYear() {
		return this.releaseYear;
	}

	public void setReleaseYear(Short releaseYear) {
		this.releaseYear = releaseYear;
	}

	public byte getRentalDuration() {
		return this.rentalDuration;
	}

	public void setRentalDuration(byte rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public BigDecimal getRentalRate() {
		return this.rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
	}

	public BigDecimal getReplacementCost() {
		return this.replacementCost;
	}

	public void setReplacementCost(BigDecimal replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Language getLanguageVO() {
		return this.languageVO;
	}

	public void setLanguageVO(Language languageVO) {
		this.languageVO = languageVO;
	}

	public Language getLanguage2() {
		return this.language2;
	}

	public void setLanguage2(Language language2) {
		this.language2 = language2;
	}

	public List<FilmActor> getFilmActors() {
		return this.filmActors;
	}

	public void setFilmActors(List<FilmActor> filmActors) {
		this.filmActors = filmActors;
	}

	public FilmActor addFilmActor(FilmActor filmActor) {
		getFilmActors().add(filmActor);
		filmActor.setFilm(this);

		return filmActor;
	}

	public FilmActor removeFilmActor(FilmActor filmActor) {
		getFilmActors().remove(filmActor);
		filmActor.setFilm(null);

		return filmActor;
	}

	public List<FilmCategory> getFilmCategories() {
		return this.filmCategories;
	}

	public void setFilmCategories(List<FilmCategory> filmCategories) {
		this.filmCategories = filmCategories;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(description);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return Objects.equals(description, other.description);
	}
	
	@Override
	public String toString() {
		return "Film [filmId=" + filmId + ", description=" + description + ", lastUpdate=" + lastUpdate + ", length="
				+ length + ", rating=" + rating + ", releaseYear=" + releaseYear + ", rentalDuration=" + rentalDuration
				+ ", rentalRate=" + rentalRate + ", replacementCost=" + replacementCost + ", title=" + title
				+ ", languageVO=" + languageVO + ", language2=" + language2 + "]";
	}

	public FilmCategory addFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().add(filmCategory);
		filmCategory.setFilm(this);

		return filmCategory;
	}

	public FilmCategory removeFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().remove(filmCategory);
		filmCategory.setFilm(null);

		return filmCategory;
	}


}