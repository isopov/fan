package com.sopovs.moradanen.fan.dto;

import lombok.Getter;

import org.joda.time.LocalDateTime;

import com.mysema.query.annotations.QueryProjection;
import com.sopovs.moradanen.fan.domain.Team;

@Getter
public class CumulativeGoals {

    private final Team team;
    private final LocalDateTime gameDate;
    private final Integer goals;

    @QueryProjection
    public CumulativeGoals(Team team, LocalDateTime gameDate, Integer goals) {
        this.team = team;
        this.gameDate = gameDate;
        this.goals = goals;
    }

}
