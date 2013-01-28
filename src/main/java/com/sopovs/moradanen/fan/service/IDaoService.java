package com.sopovs.moradanen.fan.service;

import java.util.List;
import java.util.UUID;

import com.sopovs.moradanen.fan.domain.*;

public interface IDaoService {
	Club findClubByName(String name);

	List<Club> listAllClubs();

    List<Season> lastSeasons();

    Season lastSeasonByClubName(String name);

    Contest findContestByName(String value);

    List<Game> lastGames(int size);

    List<TeamInGame> lastGames(UUID teamId, int size);

    List<Contest> listAllContests();
}
