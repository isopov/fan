package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "NATIONAL_TEAM")
@ForeignKey(name = "NATIONAL_TEAM_FK")
@Getter
@Setter
public class NationalTeam extends Team {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @ForeignKey(name = "TEAM_COUNTRY_FK")
    private Country country;

    @Enumerated(EnumType.STRING)
    private NationalTeamPosition position;

    @Override
    public String getTitle() {
        return country.getName();
    }

    @Override
    public String getTitle(String lang) {
        I18nCoutry i18n = country.getI18n(lang);
        if (i18n == null) {
            return country.getName();
        } else {
            return i18n.getName();
        }
    }
}
