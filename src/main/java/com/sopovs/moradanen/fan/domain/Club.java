package com.sopovs.moradanen.fan.domain;

import java.util.Map;

import javax.persistence.*;

import com.google.common.base.Preconditions;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "CLUB", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class Club extends Team implements I18nedDomain<I18nClub> {
    private static final long serialVersionUID = 1L;

    private String name;

    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER)
    @MapKey(name = "lang")
    private Map<Lang, I18nClub> i18ns;

    @ManyToOne
    @ForeignKey(name = "CLUB_STADIUM_FK")
    private Stadium homeStadium;

    public Club(String name) {
        this.name = name;
    }

    public Club() {
    }

    public void addI18n(I18nClub i18n) {
        Preconditions.checkState(i18ns.put(i18n.getLang(), i18n) == null);
        i18n.setClub(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHomeStadium(Stadium homeStadium) {
        this.homeStadium = homeStadium;
    }

    public Stadium getHomeStadium() {
        return homeStadium;
    }

    @Override
    public Map<Lang, I18nClub> getI18ns() {
        return i18ns;
    }

    public void setI18ns(Map<Lang, I18nClub> i18ns) {
        this.i18ns = i18ns;
    }

    @Override
    public I18nClub getI18n(String lang) {
        return i18ns.get(Lang.fromLang(lang));
    }

}
