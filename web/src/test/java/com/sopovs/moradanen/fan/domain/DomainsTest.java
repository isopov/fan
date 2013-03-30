package com.sopovs.moradanen.fan.domain;

import static com.sopovs.moradanen.fan.bootstrap.DbTestData.BLACKBURN_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.sopovs.moradanen.fan.AbstractTransactionalServiceTest;

public class DomainsTest extends AbstractTransactionalServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testBlackburnTeam() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Club> cq = cb.createQuery(Club.class);
        Root<Club> from = cq.from(Club.class);
        cq.where(cb.equal(from.get("name"), BLACKBURN_NAME));
        Club blackburn = em.createQuery(cq).getSingleResult();

        Assert.assertNotNull(blackburn.getId());
        Assert.assertEquals(BLACKBURN_NAME, blackburn.getName());
    }
}
