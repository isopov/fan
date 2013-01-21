package com.sopovs.moradanen.fan.domain;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

@Entity
@Table(name = "I18N_CONTEST")
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

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    @Override
    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        I18nContest that = (I18nContest) o;

        if (contest != null ? !contest.equals(that.contest) : that.contest != null) return false;
        if (lang != that.lang) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contest != null ? contest.hashCode() : 0;
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        return result;
    }
}
