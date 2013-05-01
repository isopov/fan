package com.sopovs.moradanen.fan.service;

import java.util.List;

import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Contest;
import com.sopovs.moradanen.fan.domain.Game;
import com.sopovs.moradanen.fan.domain.Player;
import com.sopovs.moradanen.fan.domain.PlayerInGame;
import com.sopovs.moradanen.fan.domain.Season;
import com.sopovs.moradanen.fan.domain.Team;

public interface IDaoService {
    Club findClubByName(String name);

    List<Club> listAllClubs();

    List<Season> lastSeasons();

    Season lastSeasonByClubName(String name);

    Contest findContestByName(String value);

    List<Game> lastGames(int size);

    List<Game> lastGamesForTeam(Long teamId, int size);

    List<Contest> listAllContests();

    List<Player> listAllPlayers();

    List<PlayerInGame> lastGamesForPlayer(Long playerId, int size);

    List<Game> lastGamesForTeam(Long teamId, int size, int startFrom);

    List<Game> lastGames(int size, int startFrom);

    List<PlayerInGame> lastGamesForPlayer(Long playerId, int size, int startFrom);

    int countGames();

    int countGamesForTeam(Long teamId);

    int countGamesForPlayer(Long playerId);

    List<Team> teamsPlayedWith(Long teamId, int size);

    List<Game> getGames(Long firstTeamId, Long secondTeamId);

    List<Game> getGames(Long firstTeamId, Long secondTeamId, int size, int startFrom);
}
