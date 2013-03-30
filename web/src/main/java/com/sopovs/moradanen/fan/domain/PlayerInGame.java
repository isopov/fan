package com.sopovs.moradanen.fan.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "PLAYER_IN_GAME")
public class PlayerInGame extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = CascadeType.ALL)
    @ForeignKey(name = "PLAYER_IN_GAME_PLAYER_IN_TEAM_FK")
    private PlayerInTeam playerInTeam;

    @ManyToOne
    @ForeignKey(name = "PLAYER_IN_GAME_TEAM_IN_GAME_FK")
    private TeamInGame teamInGame;

    @OneToMany(mappedBy = "scorer", cascade = CascadeType.ALL)
    private List<Goal> goals;

    // As stated in the application form, but not what was the real position
    // during the game
    @Enumerated(EnumType.STRING)
    private PlayerInGamePosition position;

    private Integer captainStart;
    private Integer minuteStart;
    private Integer minuteEnd;

    private Integer fouls;
    // There can be more than 2 only due to Judge's error
    private Integer yellowCards;
    // There can be more than 1 only due to Judge's error
    private Integer redCards;

    private Integer passes;
    private Integer faildPasses;

    private Integer shots;
    private Integer shotsOnTarget;
    private Integer saves;


    public PlayerInGame() {
    }

    public PlayerInGame(PlayerInTeam player, PlayerInGamePosition position) {
        this.playerInTeam = player;
        this.position = position;
    }

    public PlayerInGame(PlayerInTeam player, TeamInGame teamInGame, List<Goal> goals, PlayerInGamePosition position) {
        this.playerInTeam = player;
        this.teamInGame = teamInGame;
        this.goals = goals;
        this.position = position;
    }


    public PlayerInGame addGoal(Goal goal) {
        if (goals == null) {
            goals = new ArrayList<>();
        }
        goal.setScorer(this);
        goals.add(goal);
        return this;
    }

    public PlayerInGame addSaves(Integer saves) {
        this.saves = saves;
        return this;
    }

    public PlayerInGame addShotsOnTarget(Integer attemptsOnTarget) {
        this.shotsOnTarget = attemptsOnTarget;
        return this;
    }

    public PlayerInGame addShots(Integer attempts) {
        this.shots = attempts;
        return this;
    }

    public PlayerInGame addFaildPasses(Integer faildPasses) {
        this.faildPasses = faildPasses;
        return this;
    }

    public PlayerInGame addStart(Integer minuteStart) {
        this.minuteStart = minuteStart;
        return this;
    }

    public PlayerInGame addEnd(Integer minuteEnd) {
        this.minuteEnd = minuteEnd;
        return this;
    }

    public PlayerInGame addFouls(Integer fouls) {
        this.fouls = fouls;
        return this;
    }

    public PlayerInGame addYellowCards(Integer yellowCards) {
        this.yellowCards = yellowCards;
        return this;
    }

    public PlayerInGame addRedCards(Integer redCards) {
        this.redCards = redCards;
        return this;
    }

    public PlayerInGame addPasses(Integer passes) {
        this.passes = passes;
        return this;
    }


    public boolean isCaptain() {
        return captainStart != null;
    }

    public Integer getSaves() {
        return saves;
    }

    public void setSaves(Integer saves) {
        this.saves = saves;
    }

    public Integer getMinuteStart() {
        return minuteStart;
    }

    public void setMinuteStart(Integer minuteStart) {
        this.minuteStart = minuteStart;
    }

    public Integer getMinuteEnd() {
        return minuteEnd;
    }

    public void setMinuteEnd(Integer minuteEnd) {
        this.minuteEnd = minuteEnd;
    }

    public Integer getCaptainStart() {
        return captainStart;
    }

    public void setCaptainStart(Integer captainStart) {
        this.captainStart = captainStart;
    }

    public Integer getFouls() {
        return fouls;
    }

    public void setFouls(Integer fouls) {
        this.fouls = fouls;
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

    public Integer getPasses() {
        return passes;
    }

    public void setPasses(Integer passes) {
        this.passes = passes;
    }

    public Integer getFaildPasses() {
        return faildPasses;
    }

    public void setFaildPasses(Integer faildPasses) {
        this.faildPasses = faildPasses;
    }

    public TeamInGame getTeamInGame() {
        return teamInGame;
    }

    public void setTeamInGame(TeamInGame teamInGame) {
        this.teamInGame = teamInGame;
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

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    public PlayerInTeam getPlayerInTeam() {
        return playerInTeam;
    }

    public void setPlayerInTeam(PlayerInTeam playerInTeam) {
        this.playerInTeam = playerInTeam;
    }

    public PlayerInGamePosition getPosition() {
        return position;
    }

    public void setPosition(PlayerInGamePosition position) {
        this.position = position;
    }

}
