package com.sopovs.moradanen.fan.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "PLAYER_IN_GAME")
@Getter
@Setter
@NoArgsConstructor
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

}
