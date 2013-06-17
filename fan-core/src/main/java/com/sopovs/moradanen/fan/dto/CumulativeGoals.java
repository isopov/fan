package com.sopovs.moradanen.fan.dto;

import org.joda.time.LocalDateTime;

import com.mysema.query.annotations.QueryProjection;

public class CumulativeGoals {

    private final Long teamId;
    private final LocalDateTime gameDate;
    private final Integer goals;

    @QueryProjection
    public CumulativeGoals(Long teamId, LocalDateTime gameDate, Integer goals) {
        this.teamId = teamId;
        this.gameDate = gameDate;
        this.goals = goals;
    }

    public Long getTeamId() {
        return teamId;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public Integer getGoals() {
        return goals;
    }

}
