package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Country extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public String code;

	public String name;

	@OneToMany(mappedBy = "country")
	public List<Division> divisions;

	@OneToMany(mappedBy = "country")
	public List<NationalTeam> nationalTeams;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Division> getDivisions() {
		return divisions;
	}

	public void setDivisions(List<Division> divisions) {
		this.divisions = divisions;
	}

	public List<NationalTeam> getNationalTeams() {
		return nationalTeams;
	}

	public void setNationalTeams(List<NationalTeam> nationalTeams) {
		this.nationalTeams = nationalTeams;
	}
}
