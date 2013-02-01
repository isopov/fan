package com.sopovs.moradanen.fan.service;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.sopovs.moradanen.fan.domain.*;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

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
        sq.select(cb.greatest(subRoot.<LocalDate>get("endDate")));
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
        cq.select(root.get("game").<Season>get("season"));

        Subquery<Club> sq = cq.subquery(Club.class);
        Root<Club> subRoot = sq.from(Club.class);
        sq.select(subRoot);
        sq.where(cb.equal(subRoot.get("name"), name));

        cq.where(cb.equal(root.get("team"), sq));
        cq.orderBy(cb.desc(root.get("game").get("season").<LocalDate>get("endDate")));

        return getSingleResultOrNull(em.createQuery(cq).setMaxResults(1));


        // The same in JPQL - I'm not really sure that Criteria is better than
        // JPQL here...
//        return getSingleResultOrNull(em.createQuery("Select t.game.season from TeamInGame  t" +
//                " where t.team = (Select c from Club c where c.name=:name)" +
//                " order by t.game.season.endDate", Season.class)
//                .setMaxResults(1).setParameter("name", name));
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
    public int countGames(){
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Game.class)));
        return em.createQuery(cq).getSingleResult().intValue();
    }

    @Override
    public List<TeamInGame> lastGamesForTeam(UUID teamId, int size, int startFrom) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TeamInGame> cq = cb.createQuery(TeamInGame.class);
        Root<TeamInGame> from = cq.from(TeamInGame.class);
        cq.where(cb.equal(from.get("team").get("id"), teamId));
        cq.orderBy(cb.desc(from.get("game").get("gameDate")));
        return em.createQuery(cq).setFirstResult(startFrom)
                .setMaxResults(size).getResultList();
    }

    @Override
    public int countGamesForTeam(UUID teamId){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<TeamInGame> from = cq.from(TeamInGame.class);
        cq.select(cb.count(from));
        cq.where(cb.equal(from.get("team").get("id"), teamId));
        return em.createQuery(cq).getSingleResult().intValue();
    }

    @Override
    public List<TeamInGame> lastGamesForTeam(UUID teamId, int size) {
        return lastGamesForTeam(teamId, size, 0);
    }


    @Override
    public List<PlayerInGame> lastGamesForPlayer(UUID playerId, int size, int startFrom) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PlayerInGame> cq = cb.createQuery(PlayerInGame.class);
        Root<PlayerInGame> from = cq.from(PlayerInGame.class);
        cq.where(cb.equal(from.get("playerInTeam").get("player").get("id"), playerId));
        cq.orderBy(cb.desc(from.get("teamInGame").get("game").get("gameDate")));
        return em.createQuery(cq).setFirstResult(startFrom).setMaxResults(100).getResultList();
    }

    @Override
    public List<PlayerInGame> lastGamesForPlayer(UUID playerId, int size) {
        return lastGamesForPlayer(playerId, size, 0);

    }

    @Override
    public int countGamesForPlayer(UUID playerId){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<PlayerInGame> from = cq.from(PlayerInGame.class);
        cq.select(cb.count(from));
        cq.where(cb.equal(from.get("playerInTeam").get("player").get("id"), playerId));
        return em.createQuery(cq).getSingleResult().intValue();
    }

}
