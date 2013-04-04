package com.sopovs.moradanen.fan.domain;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "STADIUM", uniqueConstraints = @UniqueConstraint(name = "STADIUM_NAME_UK", columnNames = "NAME"))
public class Stadium extends DefaultI18nedDomain<I18nStadium> {

    private static final long serialVersionUID = 1L;
    private String name;
    @ManyToOne
    @ForeignKey(name = "STADIUM_COUNTRY_FK")
    private Country country;

    @OneToMany(mappedBy = "stadium")
    @MapKey(name = "lang")
    private Map<Lang, I18nStadium> i18ns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public Map<Lang, I18nStadium> getI18ns() {
        return i18ns;
    }
}
