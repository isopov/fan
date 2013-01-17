package com.sopovs.moradanen.fan.domain;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="I18N_STADIUM")
public class I18nStadium implements I18nDomain<Stadium> {

    @Id
    @Enumerated(EnumType.STRING)
    private Lang lang;
    @Id
    @ManyToOne
    @ForeignKey(name = "I18N_STADIUM_STADIUM_FK")
    private Stadium stadium;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        I18nStadium that = (I18nStadium) o;

        if (lang != that.lang) return false;
        if (stadium != null ? !stadium.equals(that.stadium) : that.stadium != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = lang != null ? lang.hashCode() : 0;
        result = 31 * result + (stadium != null ? stadium.hashCode() : 0);
        return result;
    }
}
