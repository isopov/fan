package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "I18N_CONTEST")
@Getter
@Setter
public class I18nContest implements I18nDomain<Contest> {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @ForeignKey(name = "I18N_CONTEST_CONTEST_FK")
    private Contest contest;

    @Id
    @Enumerated(EnumType.STRING)
    private Lang lang;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        I18nContest that = (I18nContest) o;

        if (contest != null ? !contest.equals(that.contest) : that.contest != null)
            return false;
        if (lang != that.lang)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contest != null ? contest.hashCode() : 0;
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        return result;
    }
}
