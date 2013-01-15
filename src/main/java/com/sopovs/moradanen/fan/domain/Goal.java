package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "GOAL")
public class Goal extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private GoalType type;

	@ManyToOne
	@ForeignKey(name = "GOAL_PLAYER_IN_GAME_FK")
	private PlayerInGame scorer;

	private Integer minute;

	public GoalType getType() {
		return type;
	}

	public void setType(GoalType type) {
		this.type = type;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

}
