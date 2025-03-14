package com.example.domain.contracts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

}
