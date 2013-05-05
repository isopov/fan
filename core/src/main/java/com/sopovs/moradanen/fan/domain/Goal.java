package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "GOAL")
@Getter
@Setter
@NoArgsConstructor
public class Goal extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private GoalType type;

    @ManyToOne
    @ForeignKey(name = "GOAL_PLAYER_IN_GAME_FK")
    private PlayerInGame scorer;

    private Integer minute;

    public Goal(Integer minute) {
        this.minute = minute;
    }

    public Goal(GoalType type, Integer minute) {
        this.type = type;
        this.minute = minute;
    }

    public Goal(GoalType type, PlayerInGame scorer, Integer minute) {
        this.type = type;
        this.scorer = scorer;
        this.minute = minute;
    }

}
