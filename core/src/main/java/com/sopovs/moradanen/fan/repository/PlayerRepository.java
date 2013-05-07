package com.sopovs.moradanen.fan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopovs.moradanen.fan.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
