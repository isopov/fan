package com.sopovs.moradanen.fan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopovs.moradanen.fan.domain.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {

    Club findByName(String name);

}
