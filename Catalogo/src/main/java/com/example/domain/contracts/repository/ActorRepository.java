package com.example.domain.contracts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	<T> List<T> findByActorIdGreaterThan(int id, Class<T> type);

}
