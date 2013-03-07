package com.sopovs.moradanen.fan.domain;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "CONTEST")
public class Contest extends DefaultI18nedDomain<I18nContest> {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @ForeignKey(name = "CONTEST_CONTEST_HOLDER_FK")
    private ContestHolder holder;
    @Enumerated(EnumType.STRING)
    private ContestType position;
    private String name;

    @OneToMany(mappedBy = "contest", fetch = FetchType.EAGER)
    @MapKey(name = "lang")
    private Map<Lang, I18nContest> i18ns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContestType getPosition() {
        return position;
    }

    public void setPosition(ContestType position) {
        this.position = position;
    }

    public ContestHolder getHolder() {
        return holder;
    }

    public void setHolder(ContestHolder holder) {
        this.holder = holder;
    }

    @Override
    public Map<Lang, I18nContest> getI18ns() {
        return i18ns;
    }

    public void setI18ns(Map<Lang, I18nContest> i18ns) {
        this.i18ns = i18ns;
    }

    @Override
    public String toString() {
        return "Contest{" + "name='" + name + '\'' + '}';
    }
}
