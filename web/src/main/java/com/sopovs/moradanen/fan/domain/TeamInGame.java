package com.sopovs.moradanen.fan.domain;

import java.util.Arrays;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "TEAM_IN_GAME")
public class TeamInGame extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @ForeignKey(name = "TEAM_IN_GAME_TEAM_FK")
    private Team team;

    @OneToMany(mappedBy = "teamInGame", cascade = CascadeType.ALL)
    private List<PlayerInGame> players;

    @ManyToOne
    @ForeignKey(name = "TEAM_IN_GAME_GAME_FK")
    private Game game;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TeamPosition position;


    //Aggregation of the data available in the players collections. Need to be stored separtely since detailed players data may not be available
    private Integer shots;
    private Integer shotsOnTarget;
    private Integer goals;

    private Integer offsides;
    private Integer fouls;
    private Integer corners;
    private Integer yellowCards;
    private Integer redCards;

    private Integer passes;
    private Integer failedPasses;

    public TeamInGame other() {
        for (TeamInGame t : game.getTeamsInGame()) {
            if (!this.equals(t)) {
                return t;
            }
        }
        return null;
    }

    public boolean isHost() {
        return position == TeamPosition.HOST;
    }

    public boolean isGuest() {
        return position == TeamPosition.GUEST;
    }

    public Integer getFailedPasses() {
        return failedPasses;
    }

    public void setFailedPasses(Integer failedPasses) {
        this.failedPasses = failedPasses;
    }

    public Integer getPasses() {
        return passes;
    }

    public void setPasses(Integer passes) {
        this.passes = passes;
    }

    public Integer getOffsides() {
        return offsides;
    }

    public void setOffsides(Integer offsides) {
        this.offsides = offsides;
    }

    public Integer getFouls() {
        return fouls;
    }

    public void setFouls(Integer fouls) {
        this.fouls = fouls;
    }

    public Integer getCorners() {
        return corners;
    }

    public void setCorners(Integer corners) {
        this.corners = corners;
    }

    public Integer getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(Integer yellowCards) {
        this.yellowCards = yellowCards;
    }

    public Integer getRedCards() {
        return redCards;
    }

    public void setRedCards(Integer redCards) {
        this.redCards = redCards;
    }

    public TeamInGame() {
    }


    public TeamInGame(Team team, Game game, TeamPosition position) {
        this.team = team;
        this.game = game;
        this.position = position;
    }

    public TeamInGame(Team team, Game game, TeamPosition position, Integer goals) {
        this.team = team;
        this.game = game;
        this.position = position;
        this.goals = goals;
    }

    public TeamInGame(Team team, Game game, TeamPosition position, Integer shots, Integer shotsOnTarget, Integer goals) {
        this.team = team;
        this.game = game;
        this.position = position;
        this.shots = shots;
        this.shotsOnTarget = shotsOnTarget;
        this.goals = goals;
    }

    public TeamInGame(Team team, Game game, Integer goals) {
        this.team = team;
        this.game = game;
        this.goals = goals;
    }

    public TeamInGame(Team team, Game game, Integer shots, Integer shotsOnTarget, Integer goals) {
        this.team = team;
        this.game = game;
        this.shots = shots;
        this.shotsOnTarget = shotsOnTarget;
        this.goals = goals;
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

    public Integer getShots() {
        return shots;
    }

    public void setShots(Integer shots) {
        this.shots = shots;
    }

    public Integer getShotsOnTarget() {
        return shotsOnTarget;
    }

    public void setShotsOnTarget(Integer shotsOnTarget) {
        this.shotsOnTarget = shotsOnTarget;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
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
