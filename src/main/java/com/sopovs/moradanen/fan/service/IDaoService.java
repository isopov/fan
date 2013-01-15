package com.sopovs.moradanen.fan.service;

import java.util.List;

import com.sopovs.moradanen.fan.domain.ClubTeam;

public interface IDaoService {
	ClubTeam getByClubTeamName(String name);

	List<ClubTeam> findByClubTeamName(String name);
}
