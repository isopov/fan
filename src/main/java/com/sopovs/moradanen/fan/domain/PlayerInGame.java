package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ForeignKey;

@Entity
public class PlayerInGame extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@ForeignKey(name = "PLAYER_IN_GAME_PLAYER_FK")
	private PlayerInTeam player;

	@ManyToOne
	@ForeignKey(name = "PLAYER_IN_GAME_TEAM_FK")
	private TeamInGame team;

	@OneToMany(mappedBy = "scorer")
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

	private Integer attempts;
	private Integer attemptsOnTarget;
	private Integer saves;

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

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	public Integer getAttemptsOnTarget() {
		return attemptsOnTarget;
	}

	public void setAttemptsOnTarget(Integer attemptsOnTarget) {
		this.attemptsOnTarget = attemptsOnTarget;
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public PlayerInTeam getPlayer() {
		return player;
	}

	public void setPlayer(PlayerInTeam player) {
		this.player = player;
	}

	public PlayerInGamePosition getPosition() {
		return position;
	}

	public void setPosition(PlayerInGamePosition position) {
		this.position = position;
	}

}
