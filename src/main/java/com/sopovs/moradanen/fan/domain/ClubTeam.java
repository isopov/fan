package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@ForeignKey(name = "CLUB_TEAM_TEAM_FK")
@Table(name = "CLUB_TEAM")
public class ClubTeam extends Team {
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@ForeignKey(name = "CLUB_TEAM_CLUB_FK")
	private Club club;

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

}
