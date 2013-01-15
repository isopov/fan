package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CLUB")
public class Club extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	private String name;
	@OneToMany(mappedBy = "club")
	private List<ClubTeam> teams;

	public Club(String name, List<ClubTeam> teams) {
		this.name = name;
		this.teams = teams;
	}

	public Club() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ClubTeam> getTeams() {
		return teams;
	}

	public void setTeams(List<ClubTeam> teams) {
		this.teams = teams;
	}

}
