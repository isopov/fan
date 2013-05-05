package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "COACH_IN_TEAM")
@Getter
@Setter
public class CoachInTeam extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate startDate;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate endDate;

    @ManyToOne
    @ForeignKey(name = "COACH_IN_TEAM_COACH_FK")
    private Coach coach;

    @ManyToOne
    @ForeignKey(name = "COACH_IN_TEAM_TEAM_FK")
    private Team team;

    @Enumerated(EnumType.STRING)
    private CoachPosition position;

}
