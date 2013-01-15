package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ForeignKey;

@Entity
public class TeamInGame extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@ForeignKey(name = "TEAM_IN_GAME_TEAM_FK")
	private Team team;

	@OneToMany(mappedBy = "team")
	private List<PlayerInGame> players;

	@ManyToOne
	@ForeignKey(name = "TEAM_IN_GAME_GAME_FK")
	private Game game;

	private TeamPosition position;

	public TeamPosition getPosition() {
		return position;
	}

	public void setPosition(TeamPosition position) {
		this.position = position;
	}

	public List<PlayerInGame> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerInGame> players) {
		this.players = players;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
