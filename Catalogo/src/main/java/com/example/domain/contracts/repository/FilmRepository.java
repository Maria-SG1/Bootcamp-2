package com.example.domain.contracts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {
	List<Film> findByLengthLessThan(int length);
	List<Film> findByTitleStartingWith(String prefix);
}
