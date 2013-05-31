package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "TEAM_IN_SEASON", uniqueConstraints =
        @UniqueConstraint(name = "TEAM_IN_SEASON_TEAM_AND_SEASON_UK", columnNames = { "team_id", "season_id" }))
@Getter
@Setter
public class TeamInSeason extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @ForeignKey(name = "TEAM_IN_SEASON_TEAM_FK")
    private Team team;

    @ManyToOne
    @ForeignKey(name = "TEAM_IN_SEASON_SEASON_FK")
    private Season season;

    @OneToMany(mappedBy = "teamInSeason", cascade = CascadeType.ALL)
    private List<TeamInGame> games;

    private Integer goals;
    private Integer concededGoals;

}
