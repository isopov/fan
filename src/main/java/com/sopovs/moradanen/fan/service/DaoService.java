package com.sopovs.moradanen.fan.service;

import java.util.List;

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
    public List<Season> lastSeasons() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Season> cq = cb.createQuery(Season.class);
        Root<Season> root = cq.from(Season.class);

        Subquery<LocalDate> sq = cq.subquery(LocalDate.class);
        Root<Season> subRoot = sq.from(Season.class);
        sq.select(cb.greatest(subRoot.<LocalDate>get("end")));
        sq.where(cb.equal(subRoot.get("contest"), root.get("contest")));

        cq.where(cb.equal(root.get("end"), sq));
        List<Season> result = em.createQuery(cq).getResultList();
        return result;

        // The same in JPQL - I'm not really sure that Criteria is better than
        // JPQL here...
        // return em
        // .createQuery(
        // "Select s1 from Season s1 where s1.end = ("
        // + "    select max(s2.end)"
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
        cq.orderBy(cb.asc(root.get("game").get("season").<LocalDate>get("start")));

        return getSingleResultOrNull(em.createQuery(cq).setMaxResults(1));


        // The same in JPQL - I'm not really sure that Criteria is better than
        // JPQL here...
//        return getSingleResultOrNull(em.createQuery("Select t.game.season from TeamInGame  t" +
//                " where t.team = (Select c from Club c where c.name=:name)" +
//                " order by t.game.season.start", Season.class)
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

}
