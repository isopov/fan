package com.sopovs.moradanen.fan.service;

import static com.sopovs.moradanen.fan.domain.QClub.club;
import static com.sopovs.moradanen.fan.domain.QGame.game;
import static com.sopovs.moradanen.fan.domain.QPlayerInGame.playerInGame;
import static com.sopovs.moradanen.fan.domain.QSeason.season;
import static com.sopovs.moradanen.fan.domain.QTeamInGame.teamInGame;
import static com.sopovs.moradanen.fan.domain.QTeamInSeason.teamInSeason;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Contest;
import com.sopovs.moradanen.fan.domain.Game;
import com.sopovs.moradanen.fan.domain.Player;
import com.sopovs.moradanen.fan.domain.PlayerInGame;
import com.sopovs.moradanen.fan.domain.QSeason;
import com.sopovs.moradanen.fan.domain.QTeamInGame;
import com.sopovs.moradanen.fan.domain.Season;
import com.sopovs.moradanen.fan.domain.Team;
import com.sopovs.moradanen.fan.domain.TeamInSeason;
import com.sopovs.moradanen.fan.repository.ClubRepository;
import com.sopovs.moradanen.fan.repository.ContestRepository;
import com.sopovs.moradanen.fan.repository.GameRepository;
import com.sopovs.moradanen.fan.repository.PlayerRepository;

@Service
@Transactional
public class DaoService implements IDaoService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ContestRepository contestRepository;

    @Override
    public Club findClubByName(String name) {
        return clubRepository.findByName(name);
    }

    @Override
    public List<Club> listAllClubs() {
        return clubRepository.findAll();
    }

    @Override
    public List<Contest> listAllContests() {
        return contestRepository.findAll();
    }

    @Override
    public List<Player> listAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public List<Season> lastSeasons() {
        QSeason s = new QSeason("s");
        return new JPAQuery(em)
                .from(season)
                .where(season.endDate
                        .eq(new JPASubQuery()
                                .from(season, s)
                                .where(season.contest.eq(s.contest))
                                .unique(s.endDate.max())
                        )
                )
                .list(season);
    }

    @Override
    public Season lastSeasonByClubName(String name) {

        return new JPAQuery(em)
                .from(teamInGame)
                .where(teamInGame.teamInSeason.team.id
                        .eq(new JPASubQuery().from(club).where(club.name.eq(name)).unique(club.id)))
                .orderBy(teamInGame.teamInSeason.season.endDate.desc())
                .limit(1L)
                .uniqueResult(teamInGame.teamInSeason.season);
    }

    @Override
    public Contest findContestByName(String value) {
        return contestRepository.findByName(value);
    }

    @Override
    public List<Game> lastGames(int size) {
        return lastGames(size, 0);
    }

    @Override
    public List<Game> lastGames(int size, int startFrom) {
        return new JPAQuery(em)
                .from(game)
                .orderBy(game.gameDate.desc())
                .limit(size)
                .offset(startFrom)
                .list(game);
    }

    @Override
    public long countGames() {
        return gameRepository.count();

    }

    @Override
    public List<Game> lastGamesForTeam(Long teamId, int size, int startFrom) {
        return new JPAQuery(em).from(teamInGame)
                .where(teamInGame.teamInSeason.team.id.eq(teamId))
                .orderBy(teamInGame.game.gameDate.desc())
                .offset(startFrom)
                .limit(size)
                .list(teamInGame.game);
    }

    @Override
    public List<Team> teamsPlayedWith(Long teamId, int size) {
        QTeamInGame tg2 = new QTeamInGame("tg2");
        return new JPAQuery(em)
                .from(teamInGame)
                .where(teamInGame.game.in(
                        new JPASubQuery()
                                .from(tg2)
                                .where(tg2.teamInSeason.team.id.eq(teamId))
                                .list(tg2.game)
                        )
                        .and(teamInGame.teamInSeason.team.id.ne(teamId))
                ).groupBy(teamInGame.teamInSeason.team)
                .orderBy(teamInGame.game.gameDate.max().desc())
                .list(teamInGame.teamInSeason.team);

    }

    @Override
    public List<Game> getGames(Long firstTeamId, Long secondTeamId) {
        return getGames(firstTeamId, secondTeamId, 0, 0);
    }

    @Override
    public List<Game> getGames(Long firstTeamId, Long secondTeamId, int size, int startFrom) {

        JPAQuery q = new JPAQuery(em)
                .from(game)
                .where(new JPASubQuery()
                        .from(teamInGame)
                        .where(teamInGame.game.eq(game).and(teamInGame.teamInSeason.team.id.eq(firstTeamId)))
                        .exists()
                        .and(new JPASubQuery()
                                .from(teamInGame)
                                .where(teamInGame.game.eq(game).and(teamInGame.teamInSeason.team.id.eq(secondTeamId)))
                                .exists()
                        )
                )
                .orderBy(game.gameDate.desc());
        if (size != 0) {
            q.limit(size);
        }
        if (startFrom != 0) {
            q.offset(startFrom);
        }
        return q.list(game);

    }

    @Override
    public List<Game> lastGamesForTeam(Long teamId, int size) {
        return lastGamesForTeam(teamId, size, 0);
    }

    @Override
    public List<PlayerInGame> lastGamesForPlayer(Long playerId, int size, int startFrom) {
        return new JPAQuery(em)
                .from(playerInGame)
                .where(playerInGame.playerInTeam.player.id.eq(playerId))
                .orderBy(playerInGame.teamInGame.game.gameDate.desc())
                .limit(size)
                .offset(startFrom)
                .list(playerInGame);
    }

    @Override
    public List<PlayerInGame> lastGamesForPlayer(Long playerId, int size) {
        return lastGamesForPlayer(playerId, size, 0);

    }

    @Override
    public long countGamesForPlayer(Long playerId) {
        return new JPAQuery(em)
                .from(playerInGame)
                .where(playerInGame.playerInTeam.player.id.eq(playerId))
                .count();

    }

    @Override
    public TeamInSeason findTeamInSeasonByTeamAndSeason(Long teamId, Long seasonId) {
        return new JPAQuery(em)
                .from(teamInSeason)
                .where(teamInSeason.team.id.eq(teamId).and(teamInSeason.season.id.eq(seasonId)))
                .uniqueResult(teamInSeason);
    }

}
