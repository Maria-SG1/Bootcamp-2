package com.example.domain.contracts.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.domain.core.contracts.repositories.RepositoryWithProjections;
import com.example.domain.entities.Actor;
import com.example.domain.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Actor>, RepositoryWithProjections {
	List<Film> findByLengthLessThan(int length);
	List<Film> findByTitleStartingWith(String prefix);
	List<Film> findByLengthBetween(int val1, int val2);
	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Date fecha);
	
	<T> List<T> findByFilmIdGreaterThan(int id, Class<T> type);
}
