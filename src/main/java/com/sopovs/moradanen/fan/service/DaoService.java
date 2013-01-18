package com.sopovs.moradanen.fan.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.sopovs.moradanen.fan.domain.Season;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;
import com.sopovs.moradanen.fan.domain.Club;

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
        return getSingleResultOrNull(clubByNameQuery(name).setMaxResults(2));
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
        return em.createQuery("Select s1 from Season s1 " +
                "where s1.end = (" +
                "    select max(s2.end)" +
                "    from Season s2" +
                "    where s1.contest=s2.contest" +
                ")", Season.class).getResultList();
    }

}
