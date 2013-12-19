package com.sopovs.moradanen.fan.domain;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "CLUB", uniqueConstraints = @UniqueConstraint(name = "CLUB_NAME_UK", columnNames = "NAME"))
@ForeignKey(name = "CLUB_TEAM_FK")
@Getter
@Setter
public class Club extends Team implements I18nedDomain<I18nClub> {
    private static final long serialVersionUID = 1L;

    private String name;

    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER)
    @MapKey(name = "lang")
    @Fetch(FetchMode.JOIN)
    // TODO why does not work?
    private Map<Lang, I18nClub> i18ns;

    @ManyToOne
    @ForeignKey(name = "CLUB_STADIUM_FK")
    @JoinColumn(name = "home_stadium_id")
    private Stadium homeStadium;

    public Club(String name) {
        this.name = name;
    }

    public Club() {
    }

    public void addI18n(I18nClub i18n) {
        checkState(i18ns.put(i18n.getLang(), i18n) == null);
        i18n.setClub(this);
    }

    @Override
    public I18nClub getI18n(String lang) {
        return i18ns.get(Lang.fromLang(lang));
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getTitle(String lang) {
        I18nClub i18n = getI18n(lang);
        if (i18n != null) {
            return i18n.getName();
        } else {
            return name;
        }
    }

    @Override
    public String toString() {
        return "Club{" + name + '}';
    }
}
