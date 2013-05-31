package com.sopovs.moradanen.fan.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "PLAYER_IN_TEAM")
@Getter
@Setter
@NoArgsConstructor
public class PlayerInTeam extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate startDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @ForeignKey(name = "PLAYER_IN_TEAM_PLAYER_FK")
    private Player player;

    @ManyToOne
    @ForeignKey(name = "PLAYER_IN_TEAM_TEAM_FK")
    private Team team;

    public PlayerInTeam(Player player, Team team) {
        this.player = player;
        this.team = team;
    }

}
