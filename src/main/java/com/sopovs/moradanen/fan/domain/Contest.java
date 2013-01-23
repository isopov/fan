package com.sopovs.moradanen.fan.domain;

import javax.persistence.*;

import com.google.common.collect.Lists;
import org.hibernate.annotations.ForeignKey;

import java.util.List;
import java.util.Map;

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
}
