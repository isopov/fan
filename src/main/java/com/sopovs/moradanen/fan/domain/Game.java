package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "GAME")
public class Game extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime date;

	@OneToMany(mappedBy = "game",cascade = CascadeType.ALL)
	private List<TeamInGame> teams;

	@OneToMany(mappedBy = "game")
	private List<RefereeInGame> judges;

	@ManyToOne
	@ForeignKey(name = "GAME_STADIUM_FK")
	private Stadium stadium;

	@ManyToOne
	@ForeignKey(name = "GAME_INFORMATION_SOURCES_FK")
	private InformationSources sources;

	private Integer spectators;

	public List<Goal> getGoals() {
		// TODO goals are in PlayerInGame
		return null;
	}

	public List<TeamInGame> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamInGame> teams) {
		this.teams = teams;
	}

	public List<RefereeInGame> getJudges() {
		return judges;
	}

	public void setJudges(List<RefereeInGame> judges) {
		this.judges = judges;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}

	public Integer getSpectators() {
		return spectators;
	}

	public void setSpectators(Integer spectators) {
		this.spectators = spectators;
	}

}
