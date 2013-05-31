package com.sopovs.moradanen.fan.domain;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "TEAM_IN_GAME")
@Getter
@Setter
@NoArgsConstructor
public class TeamInGame extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @ForeignKey(name = "TEAM_IN_GAME_TEAM_IN_SEASON_FK")
    private TeamInSeason teamInSeason;

    @OneToMany(mappedBy = "teamInGame", cascade = CascadeType.ALL)
    private List<PlayerInGame> players;

    @ManyToOne
    @ForeignKey(name = "TEAM_IN_GAME_GAME_FK")
    private Game game;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TeamPosition position;

    // Aggregation of the data available in the players collections. Need to be
    // stored separately since detailed players data may not be available
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

    public TeamInGame(TeamInSeason teamInSeason, Game game, TeamPosition position) {
        this.teamInSeason = teamInSeason;
        this.game = game;
        this.position = position;
    }

    public TeamInGame(TeamInSeason teamInSeason, Game game, TeamPosition position, Integer goals) {
        this.teamInSeason = teamInSeason;
        this.game = game;
        this.position = position;
        this.goals = goals;
    }

    public TeamInGame(TeamInSeason teamInSeason, Game game, TeamPosition position, Integer shots,
            Integer shotsOnTarget, Integer goals) {
        this.teamInSeason = teamInSeason;
        this.game = game;
        this.position = position;
        this.shots = shots;
        this.shotsOnTarget = shotsOnTarget;
        this.goals = goals;
    }

    public TeamInGame(TeamInSeason teamInSeason, Game game, Integer goals) {
        this.teamInSeason = teamInSeason;
        this.game = game;
        this.goals = goals;
    }

    public TeamInGame(TeamInSeason teamInSeason, Game game, Integer shots, Integer shotsOnTarget, Integer goals) {
        this.teamInSeason = teamInSeason;
        this.game = game;
        this.shots = shots;
        this.shotsOnTarget = shotsOnTarget;
        this.goals = goals;
    }

    public TeamInGame(TeamInSeason teamInSeason, Game game, List<PlayerInGame> players) {
        this.teamInSeason = teamInSeason;
        this.players = players;
        this.game = game;
    }

    public TeamInGame(TeamInSeason teamInSeason, Game game, PlayerInGame... players) {
        this.teamInSeason = teamInSeason;
        this.players = Arrays.asList(players);
        this.game = game;
    }

    public TeamInGame(TeamInSeason teamInSeason, Game game, TeamPosition position, List<PlayerInGame> players) {
        this.teamInSeason = teamInSeason;
        this.players = players;
        this.game = game;
        this.position = position;
    }

}
