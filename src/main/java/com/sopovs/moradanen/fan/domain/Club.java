package com.sopovs.moradanen.fan.domain;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.common.base.Preconditions;

@Entity
@Table(name = "CLUB", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class Club extends Team implements I18nedDomain<I18nClub> {
	private static final long serialVersionUID = 1L;

	private String name;

	@OneToMany(mappedBy = "club", fetch = FetchType.EAGER)
	@MapKey(name = "lang")
	private Map<Lang, I18nClub> i18ns;

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
