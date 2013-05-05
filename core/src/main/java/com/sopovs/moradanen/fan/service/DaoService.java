package com.sopovs.moradanen.fan.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Contest;
import com.sopovs.moradanen.fan.domain.Game;
import com.sopovs.moradanen.fan.domain.Player;
import com.sopovs.moradanen.fan.domain.PlayerInGame;
import com.sopovs.moradanen.fan.domain.Season;
import com.sopovs.moradanen.fan.domain.Team;
import com.sopovs.moradanen.fan.domain.TeamInGame;
import com.sopovs.moradanen.fan.domain.TeamInSeason;

@Repository
@Transactional
public class DaoService implements IDaoService {

    @PersistenceContext
    private EntityManager em;

    private TypedQuery<Club> clubByNameQuery(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Club> cq = cb.createQuery(Club.class);
        Root<Club> from = cq.from(Club.class);
        cq.where(cb.equal(from.get("name"), name));

        return em.createQuery(cq);
    }

    @Override
    public Club findClubByName(String name) {
        return getSingleResultOrNull(clubByNameQuery(name).setMaxResults(1));
    }

    private static <T> T getSingleResultOrNull(final TypedQuery<T> query) {
        return Iterables.getOnlyElement(query.getResultList(), null);
    }

    @Override
    public List<Club> listAllClubs() {
        CriteriaQuery<Club> q = em.getCriteriaBuilder().createQuery(Club.class);
        q.from(Club.class);
        return em.createQuery(q).getResultList();
    }

    @Override
    public List<Contest> listAllContests() {
        CriteriaQuery<Contest> q = em.getCriteriaBuilder().createQuery(Contest.class);
        q.from(Contest.class);
        return em.createQuery(q).getResultList();
    }

    @Override
    public List<Player> listAllPlayers() {
        CriteriaQuery<Player> q = em.getCriteriaBuilder().createQuery(Player.class);
        q.from(Player.class);
        return em.createQuery(q).getResultList();
    }

    @Override
    public List<Season> lastSeasons() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Season> cq = cb.createQuery(Season.class);
        Root<Season> root = cq.from(Season.class);

        Subquery<LocalDate> sq = cq.subquery(LocalDate.class);
        Root<Season> subRoot = sq.from(Season.class);
        sq.select(cb.greatest(subRoot.<LocalDate> get("endDate")));
        sq.where(cb.equal(subRoot.get("contest"), root.get("contest")));

        cq.where(cb.equal(root.get("endDate"), sq));
        List<Season> result = em.createQuery(cq).getResultList();
        return result;

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

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Season> cq = cb.createQuery(Season.class);

        Root<TeamInGame> root = cq.from(TeamInGame.class);
        cq.select(root.get("game").<Season> get("season"));

        Subquery<Club> sq = cq.subquery(Club.class);
        Root<Club> subRoot = sq.from(Club.class);
        sq.select(subRoot);
        sq.where(cb.equal(subRoot.get("name"), name));

        cq.where(cb.equal(root.get("teamInSeason").get("team"), sq));
        cq.orderBy(cb.desc(root.get("game").get("season").<LocalDate> get("endDate")));

        return getSingleResultOrNull(em.createQuery(cq).setMaxResults(1));

        // The same in JPQL - I'm not really sure that Criteria is better than
        // JPQL here...
        // return
        // getSingleResultOrNull(em.createQuery("Select t.game.season from TeamInGame  t"
        // +
        // " where t.team = (Select c from Club c where c.name=:name)" +
        // " order by t.game.season.endDate", Season.class)
        // .setMaxResults(1).setParameter("name", name));
    }

    @Override
    public Contest findContestByName(String value) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contest> cq = cb.createQuery(Contest.class);

        Root<Contest> from = cq.from(Contest.class);
        cq.select(from);

        cq.where(cb.equal(from.get("name"), value));
        return getSingleResultOrNull(em.createQuery(cq));
    }

    @Override
    public List<Game> lastGames(int size) {
        return lastGames(size, 0);
    }

    @Override
    public List<Game> lastGames(int size, int startFrom) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Game> cq = cb.createQuery(Game.class);
        cq.orderBy(cb.desc(cq.from(Game.class).get("gameDate")));
        return em.createQuery(cq).setFirstResult(startFrom).setMaxResults(size).getResultList();
    }

    @Override
    public int countGames() {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Game.class)));
        return em.createQuery(cq).getSingleResult().intValue();
    }

    @Override
    public List<Game> lastGamesForTeam(Long teamId, int size, int startFrom) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Game> cq = cb.createQuery(Game.class);
        Root<TeamInGame> from = cq.from(TeamInGame.class);
        cq.select(from.<Game> get("game"));
        cq.where(cb.equal(from.get("teamInSeason").get("team").get("id"), teamId));
        cq.orderBy(cb.desc(from.get("game").get("gameDate")));
        return em.createQuery(cq).setFirstResult(startFrom).setMaxResults(size).getResultList();
    }

    @Override
    public int countGamesForTeam(Long teamId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<TeamInGame> from = cq.from(TeamInGame.class);
        cq.select(cb.count(from));
        cq.where(cb.equal(from.get("team").get("id"), teamId));
        return em.createQuery(cq).getSingleResult().intValue();
    }

    @Override
    public List<Team> teamsPlayedWith(Long teamId, int size) {
        TypedQuery<Object[]> query = em.createNamedQuery("teamsPlayedWith", Object[].class);
        if (size != 0) {
            query.setMaxResults(size);
        }

        return Lists.<Object[], Team> transform(query.setParameter("teamId", teamId).getResultList(),
                new Function<Object[], Team>() {
                    @Override
                    public Team apply(Object[] input) {
                        return (Team) input[0];
                    }
                });
    }

    @Override
    public List<Game> getGames(Long firstTeamId, Long secondTeamId) {
        return getGames(firstTeamId, secondTeamId, 0, 0);
    }

    @Override
    public List<Game> getGames(Long firstTeamId, Long secondTeamId, int size, int startFrom) {
        TypedQuery<Game> query = em
                .createQuery(
                        "Select g from Game g"
                                + " where exists (select 'x' from TeamInGame tg where tg.game=g and tg.teamInSeason.team.id=:firstTeamid)"
                                + " and exists (select 'x' from TeamInGame tg where tg.game=g and tg.teamInSeason.team.id=:secondTeamId)"
                                + " order by g.gameDate desc", Game.class);
        query.setParameter("firstTeamid", firstTeamId);
        query.setParameter("secondTeamId", secondTeamId);
        if (size != 0) {
            query.setMaxResults(size);
        }
        if (startFrom != 0) {
            query.setFirstResult(startFrom);
        }
        return query.getResultList();
    }

    @Override
    public List<Game> lastGamesForTeam(Long teamId, int size) {
        return lastGamesForTeam(teamId, size, 0);
    }

    @Override
    public List<PlayerInGame> lastGamesForPlayer(Long playerId, int size, int startFrom) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PlayerInGame> cq = cb.createQuery(PlayerInGame.class);
        Root<PlayerInGame> from = cq.from(PlayerInGame.class);
        cq.where(cb.equal(from.get("playerInTeam").get("player").get("id"), playerId));
        cq.orderBy(cb.desc(from.get("teamInGame").get("game").get("gameDate")));
        return em.createQuery(cq).setFirstResult(startFrom).setMaxResults(100).getResultList();
    }

    @Override
    public List<PlayerInGame> lastGamesForPlayer(Long playerId, int size) {
        return lastGamesForPlayer(playerId, size, 0);

    }

    @Override
    public int countGamesForPlayer(Long playerId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<PlayerInGame> from = cq.from(PlayerInGame.class);
        cq.select(cb.count(from));
        cq.where(cb.equal(from.get("playerInTeam").get("player").get("id"), playerId));
        return em.createQuery(cq).getSingleResult().intValue();
    }

    @Override
    public TeamInSeason findTeamInSeasonByTeamAndSeason(Long teamId, Long seasonId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TeamInSeason> cq = cb.createQuery(TeamInSeason.class);
        Root<TeamInSeason> from = cq.from(TeamInSeason.class);
        cq.where(cb.and(cb.equal(from.get("team").get("id"), teamId),
                cb.equal(from.get("season").get("id"), seasonId)));

        return getSingleResultOrNull(em.createQuery(cq));
    }

}
