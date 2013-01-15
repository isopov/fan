package com.sopovs.moradanen.fan.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sopovs.moradanen.fan.domain.ClubTeam;

@Repository
@Transactional
public class DaoService implements IDaoService {

	@PersistenceContext
	private EntityManager em;

	private TypedQuery<ClubTeam> createClubTeamQueryByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ClubTeam> cq = cb.createQuery(ClubTeam.class);
		Root<ClubTeam> from = cq.from(ClubTeam.class);
		cq.where(cb.equal(from.get("club").get("name"), name));

		return em.createQuery(cq);
	}

	@Override
	public ClubTeam getByClubTeamName(String name) {
		return createClubTeamQueryByName(name).getSingleResult();
	}

	@Override
	public List<ClubTeam> findByClubTeamName(String name) {
		return createClubTeamQueryByName(name).getResultList();
	}

}
