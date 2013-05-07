package com.sopovs.moradanen.fan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopovs.moradanen.fan.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

}
