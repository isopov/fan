package com.sopovs.moradanen.fan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopovs.moradanen.fan.domain.Contest;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    Contest findByName(String name);

}
