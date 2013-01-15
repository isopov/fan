package com.sopovs.moradanen.fan.bootstrap;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.ClubTeam;
import com.sopovs.moradanen.fan.domain.Country;
import com.sopovs.moradanen.fan.domain.Division;
import com.sopovs.moradanen.fan.domain.DivisionType;
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
			ClubTeam roversTeam = new ClubTeam();
			Club roversClub = new Club(BLACKBURN_NAME, Arrays.asList(roversTeam));
			roversTeam.setClub(roversClub);

			ClubTeam bromwichTeam = new ClubTeam();
			Club bromwichClub = new Club(WEST_BROMWICH_ALBION_NAME, Arrays.asList(bromwichTeam));
			bromwichTeam.setClub(bromwichClub);

			Country england = new Country();
			england.setCode("EN");
			england.setName("England");

			Division premier = new Division();
			england.setDivisions(Arrays.asList(premier));
			premier.setCountry(england);
			premier.setPositition(DivisionType.FIRST);
			premier.setName("Barclays Premier League");

			em.persist(roversClub);
			em.persist(roversTeam);
			em.persist(bromwichClub);
			em.persist(bromwichTeam);
			em.persist(premier);
			em.persist(england);

			Preconditions.checkNotNull(premier.getId());
		}
	}

	private boolean notCreated() {
		return service.findByClubTeamName(BLACKBURN_NAME).isEmpty();
	}
}
