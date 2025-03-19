package com.example.domain.contracts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.domain.core.contracts.repositories.RepositoryWithProjections;
import com.example.domain.entities.Actor;
import com.example.domain.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Actor>, RepositoryWithProjections {

}
