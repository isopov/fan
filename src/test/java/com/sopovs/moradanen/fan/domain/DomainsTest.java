package com.sopovs.moradanen.fan.domain;

import static com.sopovs.moradanen.fan.bootstrap.DbTestData.BLACKBURN_NAME;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.sopovs.moradanen.fan.AbstractServiceTest;

public class DomainsTest extends AbstractServiceTest {

	@PersistenceContext
	private EntityManager em;

	@Test
	public void testClubTeamsSize() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ClubTeam> cq = cb.createQuery(ClubTeam.class);
		cq.from(ClubTeam.class);
		List<ClubTeam> allClubTeams = em.createQuery(cq).getResultList();

		Assert.assertEquals(2, allClubTeams.size());
	}

	@Test
	public void testBlackburnTeam() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ClubTeam> cq = cb.createQuery(ClubTeam.class);
		Root<ClubTeam> from = cq.from(ClubTeam.class);
		cq.where(cb.equal(from.get("club").get("name"), BLACKBURN_NAME));
		ClubTeam blackburn = em.createQuery(cq).getSingleResult();

		Assert.assertNotNull(blackburn.getId());
		Assert.assertNotNull(blackburn.getClub().getId());
		Assert.assertEquals(BLACKBURN_NAME, blackburn.getClub().getName());
	}
}
