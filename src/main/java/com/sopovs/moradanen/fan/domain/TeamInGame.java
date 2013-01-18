package com.sopovs.moradanen.fan.domain;

import java.util.Arrays;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "TEAM_IN_GAME")
public class TeamInGame extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@ForeignKey(name = "TEAM_IN_GAME_TEAM_FK")
	private Team team;

	@OneToMany(mappedBy = "teamInGame",cascade = CascadeType.ALL)
	private List<PlayerInGame> players;

	@ManyToOne
	@ForeignKey(name = "TEAM_IN_GAME_GAME_FK")
	private Game game;

	@Enumerated(EnumType.STRING)
	private TeamPosition position;

    public TeamInGame() {
    }

    public TeamInGame(Team team, Game game, List<PlayerInGame> players) {
        this.team = team;
        this.players = players;
        this.game = game;
    }

    public TeamInGame(Team team, Game game, PlayerInGame... players) {
        this.team = team;
        this.players = Arrays.asList(players);
        this.game = game;
    }

    public TeamInGame(Team team, Game game, TeamPosition position, List<PlayerInGame> players) {
        this.team = team;
        this.players = players;
        this.game = game;
        this.position = position;
    }

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
