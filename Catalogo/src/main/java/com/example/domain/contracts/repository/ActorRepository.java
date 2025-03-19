package com.example.domain.contracts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.domain.core.contracts.repositories.RepositoryWithProjections;
import com.example.domain.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor>, RepositoryWithProjections {
	
	<T> List<T> findByActorIdGreaterThan(int id, Class<T> type);

}
