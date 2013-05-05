package com.sopovs.moradanen.fan.domain;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "COUNTRY")
@ForeignKey(name = "COUNTRY_CONTEST_HOLDER_FK")
@Getter
@Setter
public class Country extends ContestHolder implements I18nedDomain<I18nCoutry> {

    private static final long serialVersionUID = 1L;

    public String code;

    public String name;

    @OneToMany(mappedBy = "country")
    public List<NationalTeam> nationalTeams;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    @MapKey(name = "lang")
    public Map<Lang, I18nCoutry> i18ns;

    @Override
    public I18nCoutry getI18n(String lang) {
        return DefaultI18nedDomain.getI18n(i18ns, lang);
    }
}
