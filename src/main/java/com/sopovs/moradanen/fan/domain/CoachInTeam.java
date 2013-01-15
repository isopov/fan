package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "COACH_IN_TEAM")
public class CoachInTeam extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate start;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate end;

	@ManyToOne
	@ForeignKey(name = "COACH_IN_TEAM_COACH_FK")
	private Coach coach;

	@ManyToOne
	@ForeignKey(name = "COACH_IN_TEAM_TEAM_FK")
	private Team team;

	@Enumerated(EnumType.STRING)
	private CoachPosition position;

	public CoachPosition getPosition() {
		return position;
	}

	public void setPosition(CoachPosition position) {
		this.position = position;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
