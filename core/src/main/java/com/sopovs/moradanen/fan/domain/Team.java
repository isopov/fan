package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TEAM")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "teamsPlayedWith", query = "Select tg.teamInSeason.team, max(tg.game.gameDate) as maxDate from TeamInGame tg"
        + " where tg.game in (Select tg2.game from TeamInGame tg2 where tg2.teamInSeason.team.id=:teamId)"
        + " and tg.teamInSeason.team.id <> :teamId"
        + " group by tg.teamInSeason.team"
        + " order by maxDate desc")
public abstract class Team extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    public abstract String getTitle();

    public abstract String getTitle(String lang);
}
