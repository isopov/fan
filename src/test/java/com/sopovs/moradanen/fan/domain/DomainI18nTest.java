package com.sopovs.moradanen.fan.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.sopovs.moradanen.fan.AbstractTransactionalServiceTest;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.sopovs.moradanen.fan.AbstractServiceTest;

public class DomainI18nTest extends AbstractTransactionalServiceTest {

	@PersistenceContext
	private EntityManager em;

	@Test
	public void testI18n() {
		{
			Club club = new Club("Test1Club");
			club.setI18ns(Maps.<Lang, I18nClub> newHashMap());
			club.addI18n(new I18nClub(Lang.EN, "Test1Club-En"));
			club.addI18n(new I18nClub(Lang.RU, "Test1Club-Ru"));

			em.persist(club);
		}
		{
			Club club = new Club("Test2Club");
			club.setI18ns(Maps.<Lang, I18nClub> newHashMap());
			club.addI18n(new I18nClub(Lang.EN, "Test2Club-En"));
			club.addI18n(new I18nClub(Lang.RU, "Test2Club-Ru"));

			em.persist(club);
		}
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Club> cq = cb.createQuery(Club.class);
			Root<Club> from = cq.from(Club.class);
			cq.where(cb.equal(from.get("name"), "Test1Club"));
			Club club = em.createQuery(cq).getSingleResult();
			Assert.assertEquals(2, club.getI18ns().size());
		}
		// club.setI18ns(Maps.n)
	}
}
