package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

@Entity
public class JudgeInGame extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@ForeignKey(name = "JUDGE_IN_GAME_GAME_FK")
	private Game game;

	@ManyToOne
	@ForeignKey(name = "JUDGE_IN_GAME_JUDGE_FK")
	private Judge jugde;

	@Enumerated(EnumType.STRING)
	private JudgePosition position;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Judge getJugde() {
		return jugde;
	}

	public void setJugde(Judge jugde) {
		this.jugde = jugde;
	}

	public JudgePosition getPosition() {
		return position;
	}

	public void setPosition(JudgePosition position) {
		this.position = position;
	}

}
