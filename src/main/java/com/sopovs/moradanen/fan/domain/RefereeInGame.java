package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "REFEREE_IN_GAME")
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public RefereePosition getPosition() {
        return position;
    }

    public void setPosition(RefereePosition position) {
        this.position = position;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

}
