package com.sopovs.moradanen.fan.bootstrap;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Contest;
import com.sopovs.moradanen.fan.domain.ContestType;
import com.sopovs.moradanen.fan.domain.Country;
import com.sopovs.moradanen.fan.service.IDaoService;

@Transactional
public class DbTestData implements IDbTestData {

	public static final String WEST_BROMWICH_ALBION_NAME = "West Bromwich Albion";
	public static final String BLACKBURN_NAME = "Blackburn Rovers";

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IDaoService service;

	@Override
	public void createTestData() {
		if (notCreated()) {
			Club roversClub = new Club(BLACKBURN_NAME);

			Club bromwichClub = new Club(WEST_BROMWICH_ALBION_NAME);

			Country england = new Country();
			england.setCode("EN");
			england.setName("England");

			Contest premier = new Contest();
			england.setContests(Arrays.asList(premier));
			premier.setHolder(england);
			premier.setPositition(ContestType.FIRST);
			premier.setName("Barclays Premier League");

			em.persist(roversClub);
			em.persist(bromwichClub);
			em.persist(premier);
			em.persist(england);

			Preconditions.checkNotNull(premier.getId());
		}
	}

	private boolean notCreated() {
		return service.findClubByName(BLACKBURN_NAME) == null;
	}
}
