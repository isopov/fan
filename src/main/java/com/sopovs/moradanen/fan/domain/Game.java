package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "GAME")
public class Game extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime date;

	@OneToMany(mappedBy = "game",cascade = CascadeType.ALL)
	private List<TeamInGame> teams;

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

	public List<Goal> getGoals() {
		// TODO goals are in PlayerInGame
		return null;
	}

    public TeamInGame getGuest(){
        if(teams != null){
            for(TeamInGame t:teams){
                if(t.getPosition() == TeamPosition.GUEST){
                    return t;
                }
            }
        }
        return null;
    }
    public TeamInGame getHost(){
        if(teams != null){
            for(TeamInGame t:teams){
                if(t.getPosition() == TeamPosition.HOST){
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

    public List<TeamInGame> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamInGame> teams) {
		this.teams = teams;
	}

	public List<RefereeInGame> getJudges() {
		return judges;
	}

	public void setJudges(List<RefereeInGame> judges) {
		this.judges = judges;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
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
