package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Entity
@Table(name = "GAME")
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
    @ForeignKey(name = "GAME_INFORMATION_SOURCES_FK")
    private InformationSources sources;

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

    public InformationSources getSources() {
        return sources;
    }

    public void setSources(InformationSources sources) {
        this.sources = sources;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public List<Team> getTeams() {
        return Lists.transform(teamsInGame, new Function<TeamInGame, Team>() {
            @Override
            public Team apply(TeamInGame input) {
                return input.getTeamInSeason().getTeam();
            }
        });
    }

    public List<TeamInGame> getTeamsInGame() {
        return teamsInGame;
    }

    public void setTeamsInGame(List<TeamInGame> teamsInGame) {
        this.teamsInGame = teamsInGame;
    }

    public List<RefereeInGame> getJudges() {
        return judges;
    }

    public void setJudges(List<RefereeInGame> judges) {
        this.judges = judges;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDateTime gameDate) {
        this.gameDate = gameDate;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public Integer getSpectators() {
        return spectators;
    }

    public void setSpectators(Integer spectators) {
        this.spectators = spectators;
    }

}
