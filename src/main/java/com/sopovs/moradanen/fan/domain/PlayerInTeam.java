package com.sopovs.moradanen.fan.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "PLAYER_IN_TEAM")
public class PlayerInTeam extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startDate;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate endDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "PLAYER_IN_TEAM_PLAYER_FK")
	private Player player;

	@ManyToOne
	@ForeignKey(name = "PLAYER_IN_TEAM_TEAM_FK")
	private Team team;

    public PlayerInTeam() {
    }

    public PlayerInTeam(Player player, Team team) {
        this.player = player;
        this.team = team;
    }

    public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
