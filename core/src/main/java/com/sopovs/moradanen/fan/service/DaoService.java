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

    // private static <T> T getSingleResultOrNull(final TypedQuery<T> query) {
    // return Iterables.getOnlyElement(query.getResultList(), null);
    // }

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

        // The same in JPA Criteria:
        // CriteriaBuilder cb = em.getCriteriaBuilder();
        // CriteriaQuery<Season> cq = cb.createQuery(Season.class);
        // Root<Season> root = cq.from(Season.class);
        //
        // Subquery<LocalDate> sq = cq.subquery(LocalDate.class);
        // Root<Season> subRoot = sq.from(Season.class);
        // sq.select(cb.greatest(subRoot.<LocalDate> get("endDate")));
        // sq.where(cb.equal(subRoot.get("contest"), root.get("contest")));
        //
        // cq.where(cb.equal(root.get("endDate"), sq));
        // List<Season> result = em.createQuery(cq).getResultList();
        // return result;

        // The same in JPQL - I'm not really sure that Criteria is better than
        // JPQL here...
        // return em
        // .createQuery(
        // "Select s1 from Season s1 where s1.endDate = ("
        // + "    select max(s2.endDate)"
        // + "    from Season s2"
        // + "    where s1.contest=s2.contest" + ")",
        // Season.class).getResultList();
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

        // The same in JPA Criteria:
        // CriteriaBuilder cb = em.getCriteriaBuilder();
        // CriteriaQuery<Season> cq = cb.createQuery(Season.class);
        //
        // Root<TeamInGame> root = cq.from(TeamInGame.class);
        // cq.select(root.get("game").<Season> get("season"));
        //
        // Subquery<Club> sq = cq.subquery(Club.class);
        // Root<Club> subRoot = sq.from(Club.class);
        // sq.select(subRoot);
        // sq.where(cb.equal(subRoot.get("name"), name));
        //
        // cq.where(cb.equal(root.get("teamInSeason").get("team"), sq));
        // cq.orderBy(cb.desc(root.get("game").get("season").<LocalDate>
        // get("endDate")));
        //
        // return getSingleResultOrNull(em.createQuery(cq).setMaxResults(1));

        // The same in JPQL - I'm not really sure that Criteria is better than
        // JPQL here...
        // return
        // getSingleResultOrNull(em.createQuery("Select t.game.season from TeamInGame  t"
        // +
        // " where t.teamInSeason.team = (Select c from Club c where c.name=:name)"
        // +
        // " order by t.game.season.endDate", Season.class)
        // .setMaxResults(1).setParameter("name", name));
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
        // CriteriaBuilder cb = em.getCriteriaBuilder();
        // CriteriaQuery<Game> cq = cb.createQuery(Game.class);
        // cq.orderBy(cb.desc(cq.from(Game.class).get("gameDate")));
        // return
        // em.createQuery(cq).setFirstResult(startFrom).setMaxResults(size).getResultList();
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

        // CriteriaBuilder cb = em.getCriteriaBuilder();
        // CriteriaQuery<Game> cq = cb.createQuery(Game.class);
        // Root<TeamInGame> from = cq.from(TeamInGame.class);
        // cq.select(from.<Game> get("game"));
        // cq.where(cb.equal(from.get("teamInSeason").get("team").get("id"),
        // teamId));
        // cq.orderBy(cb.desc(from.get("game").get("gameDate")));
        // return
        // em.createQuery(cq).setFirstResult(startFrom).setMaxResults(size).getResultList();
    }

    // @NamedQuery(name = "teamsPlayedWith", query =
    // "Select tg.teamInSeason.team, max(tg.game.gameDate) as maxDate from TeamInGame tg"
    // +
    // " where tg.game in (Select tg2.game from TeamInGame tg2 where tg2.teamInSeason.team.id=:teamId)"
    // + " and tg.teamInSeason.team.id <> :teamId"
    // + " group by tg.teamInSeason.team"
    // + " order by maxDate desc")

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

        // TypedQuery<Game> query = em
        // .createQuery(
        // "Select g from Game g"
        // +
        // " where exists (select 'x' from TeamInGame tg where tg.game=g and tg.teamInSeason.team.id=:firstTeamid)"
        // +
        // " and exists (select 'x' from TeamInGame tg where tg.game=g and tg.teamInSeason.team.id=:secondTeamId)"
        // + " order by g.gameDate desc", Game.class);
        // query.setParameter("firstTeamid", firstTeamId);
        // query.setParameter("secondTeamId", secondTeamId);
        // if (size != 0) {
        // query.setMaxResults(size);
        // }
        // if (startFrom != 0) {
        // query.setFirstResult(startFrom);
        // }
        // return query.getResultList();
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

        // CriteriaBuilder cb = em.getCriteriaBuilder();
        // CriteriaQuery<PlayerInGame> cq = cb.createQuery(PlayerInGame.class);
        // Root<PlayerInGame> from = cq.from(PlayerInGame.class);
        // cq.where(cb.equal(from.get("playerInTeam").get("player").get("id"),
        // playerId));
        // cq.orderBy(cb.desc(from.get("teamInGame").get("game").get("gameDate")));
        // return
        // em.createQuery(cq).setFirstResult(startFrom).setMaxResults(100).getResultList();
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

        // CriteriaBuilder cb = em.getCriteriaBuilder();
        // CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        // Root<PlayerInGame> from = cq.from(PlayerInGame.class);
        // cq.select(cb.count(from));
        // cq.where(cb.equal(from.get("playerInTeam").get("player").get("id"),
        // playerId));
        // return em.createQuery(cq).getSingleResult().intValue();
    }

    @Override
    public TeamInSeason findTeamInSeasonByTeamAndSeason(Long teamId, Long seasonId) {
        return new JPAQuery(em)
                .from(teamInSeason)
                .where(teamInSeason.team.id.eq(teamId).and(teamInSeason.season.id.eq(seasonId)))
                .uniqueResult(teamInSeason);
    }

}
