package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Entity
@Table(name = "GAME")
@Getter
@Setter
public class Game extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime gameDate;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<TeamInGame> teamsInGame;

    @OneToMany(mappedBy = "game")
    private List<RefereeInGame> judges;

    @ManyToOne
    @ForeignKey(name = "GAME_STADIUM_FK")
    private Stadium stadium;

    @ManyToOne
    @ForeignKey(name = "GAME_SEASON_FK")
    private Season season;

    private Integer spectators;

    public TeamInGame getGuest() {
        if (teamsInGame != null) {
            for (TeamInGame t : teamsInGame) {
                if (t.getPosition() == TeamPosition.GUEST) {
                    return t;
                }
            }
        }
        return null;
    }

    public void setGuest(TeamInGame guest) {
        throw new IllegalStateException();
    }

    public TeamInGame getHost() {
        if (teamsInGame != null) {
            for (TeamInGame t : teamsInGame) {
                if (t.getPosition() == TeamPosition.HOST) {
                    return t;
                }
            }
        }
        return null;
    }

    public List<Team> getTeams() {
        return Lists.transform(teamsInGame, new Function<TeamInGame, Team>() {
            @Override
            public Team apply(TeamInGame input) {
                return input.getTeamInSeason().getTeam();
            }
        });
    }

}
