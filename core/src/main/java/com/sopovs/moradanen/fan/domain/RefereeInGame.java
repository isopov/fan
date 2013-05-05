package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "REFEREE_IN_GAME")
@Getter
@Setter
public class RefereeInGame extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @ForeignKey(name = "REFEREE_IN_GAME_GAME_FK")
    private Game game;

    @ManyToOne
    @ForeignKey(name = "REFEREE_IN_GAME_REFEREE_FK")
    private Referee referee;

    @Enumerated(EnumType.STRING)
    private RefereePosition position;

}
