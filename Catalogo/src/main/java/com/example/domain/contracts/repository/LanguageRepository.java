package com.example.domain.contracts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

}
