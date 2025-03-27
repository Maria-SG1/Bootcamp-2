package com.example.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.domain.core.entities.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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

	@Column(name="last_update", nullable=false, insertable = false, updatable = false)
	@PastOrPresent
	private Timestamp lastUpdate;

	@Column(name = "length")
	@Positive
	@Min(15)
	@Max(210)
	private Integer length;

	@Column(length=1)
	@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$", message = "Rating inválido. Valores permitidos: G, PG, PG-13, R, NC-17.")
	private String rating;

	@Column(name="release_year")
//	@NotNull
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
	@JsonIgnore
	private Language languageVO;

	@ManyToOne
	@JoinColumn(name="original_language_id")
	@JsonIgnore
	private Language language;

	

	@OneToMany(mappedBy="film", 
			cascade = CascadeType.ALL, orphanRemoval = true)
	@Valid
	@JsonIgnore
	@JsonBackReference
	private List<FilmActor> filmActors;

	@OneToMany(mappedBy="film", 
			cascade = CascadeType.ALL, orphanRemoval = true)
	@Valid
	@JsonIgnore
	@JsonBackReference
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

	public Film(int filmId, @Size(max = 255) String description, 
//			@PastOrPresent Timestamp lastUpdate,
			@Positive @Min(15) @Max(210) Integer length,
//			@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$", message = "Rating inválido. Valores permitidos: G, PG, PG-13, R, NC-17.") String rating,
			@Min(1920) @Max(2030) Short releaseYear, @Positive byte rentalDuration,
			@DecimalMin("0.01") @DecimalMax("1000.00") BigDecimal rentalRate,
			@DecimalMin("0.01") BigDecimal replacementCost, @NotBlank @Size(max = 128, min = 2) String title,
			Language languageVO, Language language2) {
		super();
		this.filmId = filmId;
		this.description = description;
//		this.lastUpdate = lastUpdate;
		this.length = length;
//		this.rating = rating;
		this.releaseYear = releaseYear;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.replacementCost = replacementCost;
		this.title = title;
		this.languageVO = languageVO;
		this.language = language2;
		this.filmActors = new ArrayList<>();
		this.filmCategories = new ArrayList<>();
	}
	
	public Film(int filmId, @Size(max = 255) String description, @Positive @Min(15) @Max(210) Integer length,
			@Min(1920) @Max(2030) Short releaseYear, @NotNull @Min(1) @Max(10) byte rentalDuration,
			@NotNull @DecimalMin("0.01") @DecimalMax("1000.00") BigDecimal rentalRate,
			@NotNull @DecimalMin("0.01") BigDecimal replacementCost, @NotBlank @Size(max = 128, min = 2) String title) {
		super();
		this.filmId = filmId;
		this.description = description;
		this.length = length;
		this.releaseYear = releaseYear;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.replacementCost = replacementCost;
		this.title = title;
		this.filmActors = new ArrayList<>();
		this.filmCategories = new ArrayList<>();
	}

	public Film(int filmId, @Size(max = 255) String description, @Positive @Min(15) @Max(210) Integer length,
			@Min(1920) @Max(2030) Short releaseYear, @NotBlank @Size(max = 128, min = 2) String title,
//			@NotNull Language languageVO, 
			Language language2) {
		super();
		this.filmId = filmId;
		this.description = description;
		this.length = length;
		this.releaseYear = releaseYear;
		this.title = title;
//		this.languageVO = languageVO;
		this.language = language2;
		this.filmActors = new ArrayList<>();
		this.filmCategories = new ArrayList<>();
	}

	public Film(@NotBlank @Size(max = 128, min = 2) String title, Language language2) {
		super();
		this.title = title;
		this.language = language2;
		this.filmActors = new ArrayList<>();
		this.filmCategories = new ArrayList<>();
	}
	
	public Film(int filmId, @NotBlank @Size(max = 128) String title, String description, @Min(1895) Short releaseYear,
			@NotNull Language language, Language languageVO, @Positive byte rentalDuration,
			@Positive @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 2, fraction = 2) BigDecimal rentalRate,
			@Positive Integer length,
			@DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 3, fraction = 2) BigDecimal replacementCost
			) {
		super();
		this.filmId = filmId;
		this.title = title;
		this.description = description;
		this.releaseYear = releaseYear;
		this.language = language;
		this.languageVO = languageVO;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.length = length;
		this.replacementCost = replacementCost;
		
	}


	public int getFilmId() {
		return this.filmId;
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

	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
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
	
	
//				 public void addActor1(Actor actor) {
//				        FilmActor filmActor = new FilmActor();
//				        filmActor.setFilm(this);
//				        filmActor.setActor(actor);
//				        this.filmActors.add(filmActor);
//				    }
//			
//				    public void removeActor1(Actor actor) {
//				        filmActors.removeIf(filmActor -> filmActor.getActor().equals(actor));
//				    }    
	    

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
				+ ", languageVO=" + languageVO + ", language2=" + language + "]";
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

//
	
	public List<Actor> getActors() {
		return this.filmActors.stream().map(item -> item.getActor()).toList();
	}

	public void setActors(List<Actor> source) {
		if (filmActors == null || !filmActors.isEmpty())
			clearActors();
		source.forEach(item -> addActor(item));
	}
	
	public void clearActors() {
		filmActors = new ArrayList<FilmActor>();
	}
	
	public void addActor(Actor actor) {
		FilmActor filmActor = new FilmActor(this,actor);
		filmActors.add(filmActor);
	}

	public void addActor(int actorId) {
		addActor(new Actor(actorId));
	}

	public void removeActor(Actor actor) {
		var filmActor = filmActors.stream().filter(item -> item.getActor().equals(actor)).findFirst();
		if (filmActor.isEmpty())
			return;
		filmActors.remove(filmActor.get());
	}

	public void removeActor(int actorId) {
		removeActor(new Actor(actorId));
	}
	
	
	public List<Category> getCategories() {
		return this.filmCategories.stream().map(item -> item.getCategory()).toList();
	}

	public void setCategories(List<Category> source) {
		if (filmCategories == null || !filmCategories.isEmpty())
			clearCategories();
		source.forEach(item -> addCategory(item));
	}

	public void clearCategories() {
		filmCategories = new ArrayList<FilmCategory>();
	}

	public void addCategory(Category item) {
		FilmCategory filmCategory = new FilmCategory(this,item);
		filmCategories.add(filmCategory);
	}

	public void addCategory(int id) {
		addCategory(new Category(id));
	}

	public void removeCategory(Category ele) {
		var filmCategory = filmCategories.stream().filter(item -> item.getCategory().equals(ele)).findFirst();
		if (filmCategory.isEmpty())
			return;
		filmCategories.remove(filmCategory.get());
	}

	public void removeCategory(int id) {
		removeCategory(new Category(id));
	}
	
	
	
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "film_id", unique = true, nullable = false)
//	private int filmId;
//
//	@Lob
//	private String description;
//
//	@Column(name = "last_update", insertable = false, updatable = false, nullable = false)
//	private Date lastUpdate;
//
//	@Positive
//	private Integer length;
//
//
//
//	// @Temporal(TemporalType.DATE)
//	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
//	@Min(1901)
//	@Max(2155)
//	@Column(name = "release_year")
//	private Short releaseYear;
//
//	@NotNull
//	@Positive
//	@Column(name = "rental_duration", nullable = false)
//	private byte rentalDuration;
//
//	@NotNull
//	@Digits(integer = 2, fraction = 2)
//	@DecimalMin(value = "0.0", inclusive = false)
//	@Column(name = "rental_rate", nullable = false, precision = 10, scale = 2)
//	private BigDecimal rentalRate;
//
//	@NotNull
//	@Digits(integer = 3, fraction = 2)
//	@DecimalMin(value = "0.0", inclusive = false)
//	@Column(name = "replacement_cost", nullable = false, precision = 10, scale = 2)
//	private BigDecimal replacementCost;
//
//	@NotBlank
//	@Size(max = 128)
//	@Column(nullable = false, length = 128)
//	private String title;
//	
//	
//	// bi-directional many-to-one association to Language
//	@ManyToOne
//	@JoinColumn(name = "language_id")
//	@NotNull
//	@JsonManagedReference
//	private Language language;
//
//	// bi-directional many-to-one association to Language
//	@ManyToOne
//	@JoinColumn(name = "original_language_id")
//	@JsonManagedReference
//	private Language languageVO;
//
//	// bi-directional many-to-one association to FilmActor
//	@OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
//	@JsonBackReference
//	private List<FilmActor> filmActors = new ArrayList<FilmActor>();
//
//	// bi-directional many-to-one association to FilmCategory
//	@OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
//	@JsonBackReference
//	private List<FilmCategory> filmCategories = new ArrayList<FilmCategory>();
//
//	public Film() {
//	}
//
//	public Film(int filmId) {
//		this.filmId = filmId;
//	}
//	
//							public Film(int filmId, String title) {
//							super();
//							this.filmId = filmId;
//							this.title = title;
//							this.filmActors = new ArrayList<>();
//							this.filmCategories = new ArrayList<>();
//						}	
//
//	public Film(@NotBlank @Size(max = 128) String title, @NotNull Language language, @Positive byte rentalDuration,
//			@Positive @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 2, fraction = 2) BigDecimal rentalRate,
//			@DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 3, fraction = 2) BigDecimal replacementCost) {
//		super();
//		this.title = title;
//		this.language = language;
//		this.rentalDuration = rentalDuration;
//		this.rentalRate = rentalRate;
//		this.replacementCost = replacementCost;
//	}
//
//	public Film(int filmId, @NotBlank @Size(max = 128) String title, @NotNull Language language,
//			@NotNull @Positive byte rentalDuration,
//			@NotNull @Digits(integer = 2, fraction = 2) @DecimalMin(value = "0.0", inclusive = false) BigDecimal rentalRate,
//			@NotNull @Digits(integer = 3, fraction = 2) @DecimalMin(value = "0.0", inclusive = false) BigDecimal replacementCost) {
//		super();
//		this.filmId = filmId;
//		this.title = title;
//		this.language = language;
//		this.rentalDuration = rentalDuration;
//		this.rentalRate = rentalRate;
//		this.replacementCost = replacementCost;
//	}
//
//	public Film(int filmId, @NotBlank @Size(max = 128) String title, String description, @Min(1895) Short releaseYear,
//			@NotNull Language language, Language languageVO, @Positive byte rentalDuration,
//			@Positive @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 2, fraction = 2) BigDecimal rentalRate,
//			@Positive Integer length,
//			@DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 3, fraction = 2) BigDecimal replacementCost) {
//		super();
//		this.filmId = filmId;
//		this.title = title;
//		this.description = description;
//		this.releaseYear = releaseYear;
//		this.language = language;
//		this.languageVO = languageVO;
//		this.rentalDuration = rentalDuration;
//		this.rentalRate = rentalRate;
//		this.length = length;
//		this.replacementCost = replacementCost;
//		
//	}
//
//	public int getFilmId() {
//		return this.filmId;
//	}
//
//	public void setFilmId(int filmId) {
//		this.filmId = filmId;
//		if (filmActors != null && filmActors.size() > 0)
//			filmActors.forEach(item -> {
//				if (item.getId().getFilmId() != filmId)
//					item.getId().setFilmId(filmId);
//			});
//		if (filmCategories != null && filmCategories.size() > 0)
//			filmCategories.forEach(item -> {
//				if (item.getId().getFilmId() != filmId)
//					item.getId().setFilmId(filmId);
//			});
//	}
//
//	public String getDescription() {
//		return this.description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public Date getLastUpdate() {
//		return this.lastUpdate;
//	}
//
//	public void setLastUpdate(Date lastUpdate) {
//		this.lastUpdate = lastUpdate;
//	}
//
//	public Integer getLength() {
//		return this.length;
//	}
//
//	public void setLength(Integer length) {
//		this.length = length;
//	}
//
//	
//	public Short getReleaseYear() {
//		return this.releaseYear;
//	}
//
//	public void setReleaseYear(Short releaseYear) {
//		this.releaseYear = releaseYear;
//	}
//
//	public byte getRentalDuration() {
//		return this.rentalDuration;
//	}
//
//	public void setRentalDuration(byte rentalDuration) {
//		this.rentalDuration = rentalDuration;
//	}
//
//	public BigDecimal getRentalRate() {
//		return this.rentalRate;
//	}
//
//	public void setRentalRate(BigDecimal rentalRate) {
//		this.rentalRate = rentalRate;
//	}
//
//	public BigDecimal getReplacementCost() {
//		return this.replacementCost;
//	}
//
//	public void setReplacementCost(BigDecimal replacementCost) {
//		this.replacementCost = replacementCost;
//	}
//
//	public String getTitle() {
//		return this.title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public Language getLanguage() {
//		return this.language;
//	}
//
//	public void setLanguage(Language language) {
//		this.language = language;
//	}
//
//	public Language getLanguageVO() {
//		return this.languageVO;
//	}
//
//	public void setLanguageVO(Language languageVO) {
//		this.languageVO = languageVO;
//	}
//
//	// Gestión de actores
//	
//							public List<FilmActor> getFilmActors() {
//							return this.filmActors;
//						}
//						
//						public void setFilmActors(List<FilmActor> filmActors) {
//							this.filmActors = filmActors;
//						}
//
//	public List<Actor> getActors() {
//		return this.filmActors.stream().map(item -> item.getActor()).toList();
//	}
//
//	public void setActors(List<Actor> source) {
//		if (filmActors == null || !filmActors.isEmpty())
//			clearActors();
//		source.forEach(item -> addActor(item));
//	}
//
//	public void clearActors() {
//		filmActors = new ArrayList<FilmActor>();
//	}
//
//	public void addActor(Actor actor) {
//		FilmActor filmActor = new FilmActor(this, actor);
//		filmActors.add(filmActor);
//	}
//
//	public void addActor(int actorId) {
//		addActor(new Actor(actorId));
//	}
//
//	public void removeActor(Actor actor) {
//		var filmActor = filmActors.stream().filter(item -> item.getActor().equals(actor)).findFirst();
//		if (filmActor.isEmpty())
//			return;
//		filmActors.remove(filmActor.get());
//	}
//
//	public void removeActor(int actorId) {
//		removeActor(new Actor(actorId));
//	}
//
//	// Gestión de categorias
//	
//						public List<FilmCategory> getFilmCategories() {
//						return this.filmCategories;
//					}
//					
//					public void setFilmCategories(List<FilmCategory> filmCategories) {
//						this.filmCategories = filmCategories;
//					}
//	
//
//	public List<Category> getCategories() {
//		return this.filmCategories.stream().map(item -> item.getCategory()).toList();
//	}
//
//	public void setCategories(List<Category> source) {
//		if (filmCategories == null || !filmCategories.isEmpty())
//			clearCategories();
//		source.forEach(item -> addCategory(item));
//	}
//
//	public void clearCategories() {
//		filmCategories = new ArrayList<FilmCategory>();
//	}
//
//	public void addCategory(Category item) {
//		FilmCategory filmCategory = new FilmCategory(this, item);
//		filmCategories.add(filmCategory);
//	}
//
//	public void addCategory(int id) {
//		addCategory(new Category(id));
//	}
//
//	public void removeCategory(Category ele) {
//		var filmCategory = filmCategories.stream().filter(item -> item.getCategory().equals(ele)).findFirst();
//		if (filmCategory.isEmpty())
//			return;
//		filmCategories.remove(filmCategory.get());
//	}
//
//	public void removeCategory(int id) {
//		removeCategory(new Category(id));
//	}
//
//	
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(filmId);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj instanceof Film o)
//			return filmId == o.filmId;
//		else
//			return false;
//	}
//
//	@Override
//	public String toString() {
//		return "Film [filmId=" + filmId + ", title=" + title + ", rentalDuration=" + rentalDuration + ", rentalRate="
//				+ rentalRate + ", replacementCost=" + replacementCost + ", lastUpdate=" + lastUpdate + ", description="
//				+ description + ", length=" + length + ", releaseYear=" + releaseYear
//				+ ", language=" + language + ", languageVO=" + languageVO + "]";
//	}
//
//	public Film merge(Film target) {
////		BeanUtils.copyProperties(this, target, "filmId" , "filmActors", "filmCategories");
//		target.title = title;
//		target.description = description;
//		target.releaseYear = releaseYear;
//		target.language = language;
//		target.languageVO = languageVO;
//		target.rentalDuration = rentalDuration;
//		target.rentalRate = rentalRate;
//		target.length = length;
//		target.replacementCost = replacementCost;
//	
//		// Borra los actores que sobran
//		target.getActors().stream().filter(item -> !getActors().contains(item))
//				.forEach(item -> target.removeActor(item));
//		// Añade los actores que faltan
//		getActors().stream().filter(item -> !target.getActors().contains(item)).forEach(item -> target.addActor(item));
//		// Borra las categorias que sobran
//		target.getCategories().stream().filter(item -> !getCategories().contains(item))
//				.forEach(item -> target.removeCategory(item));
//		// Añade las categorias que faltan
//		getCategories().stream().filter(item -> !target.getCategories().contains(item))
//				.forEach(item -> target.addCategory(item));
//		
//		// Bug de Hibernate
//		target.filmActors.forEach(o -> o.prePersiste());
//		target.filmCategories.forEach(o -> o.prePersiste());
//		
//		return target;
//	}
//	
//	// Bug de Hibernate
//	@PostPersist
//	@PostUpdate
//	public void prePersiste() {
//		System.err.println("prePersiste(): Bug Hibernate");
//		filmActors.forEach(o -> o.prePersiste());
//		filmCategories.forEach(o -> o.prePersiste());
//	}


}