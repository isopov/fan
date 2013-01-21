package com.sopovs.moradanen.fan.service;

import java.util.List;

import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Season;

public interface IDaoService {
	Club findClubByName(String name);

	List<Club> listAllClubs();

    List<Season> lastSeasons();

    Season lastSeasonByClubName(String name);
}
