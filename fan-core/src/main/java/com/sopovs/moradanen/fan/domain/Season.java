package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import com.google.common.collect.Lists;

@Entity
@Table(name = "SEASON")
@Getter
@Setter
public class Season extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @NotNull
    private LocalDate startDate;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @NotNull
    private LocalDate endDate;

    @ManyToOne
    @ForeignKey(name = "SEASON_CONTEST_FK")
    private Contest contest;

    @OneToMany(mappedBy = "season")
    private List<Game> games;

    @OneToMany(mappedBy = "season")
    private List<TeamInSeason> teamsInSeason;

    public void addGame(Game game) {
        if (games == null) {
            games = Lists.newArrayList();
        }
        games.add(game);
    }

    @Override
    public String toString() {
        return "Season{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", contest=" + contest +
                '}';
    }
}
