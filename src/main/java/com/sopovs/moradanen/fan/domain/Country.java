package com.sopovs.moradanen.fan.domain;

import java.util.List;
import java.util.Map;

import javax.persistence.*;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "COUNTRY")
@ForeignKey(name = "COUNTRY_CONTEST_HOLDER_FK")
public class Country extends ContestHolder  implements I18nedDomain<I18nCoutry>{

	private static final long serialVersionUID = 1L;

	public String code;

	public String name;

	@OneToMany(mappedBy = "country")
	public List<NationalTeam> nationalTeams;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    @MapKey(name = "lang")
    public Map<Lang,I18nCoutry> i18ns;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<NationalTeam> getNationalTeams() {
		return nationalTeams;
	}

	public void setNationalTeams(List<NationalTeam> nationalTeams) {
		this.nationalTeams = nationalTeams;
	}

    @Override
    public Map<Lang, I18nCoutry> getI18ns() {
        return i18ns;
    }

    @Override
    public I18nCoutry getI18n(String lang) {
        return DefaultI18nedDomain.getI18n(i18ns,lang);
    }
}
