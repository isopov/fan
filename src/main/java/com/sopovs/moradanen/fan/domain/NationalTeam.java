package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "NATIONAL_TEAM")
@ForeignKey(name = "NATIONAL_TEAM_FK")
public class NationalTeam extends Team {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@ForeignKey(name = "TEAM_COUNTRY_FK")
	private Country country;

	@Enumerated(EnumType.STRING)
	private NationalTeamPosition position;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public NationalTeamPosition getPosition() {
		return position;
	}

	public void setPosition(NationalTeamPosition position) {
		this.position = position;
	}

}
