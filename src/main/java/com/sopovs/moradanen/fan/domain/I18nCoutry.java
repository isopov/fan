package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "I18N_COUNTRY")
public class I18nCoutry implements I18nDomain<Country> {

	private static final long serialVersionUID = 1L;
	@Id
	@Enumerated(EnumType.STRING)
	private Lang lang;
	@Id
	@ManyToOne
	@ForeignKey(name = "I18N_COUNTRY_COUNTRY_FK")
	private Country country;

	private String name;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
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
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		I18nCoutry that = (I18nCoutry) o;

		if (country != null ? !country.equals(that.country)
				: that.country != null)
			return false;
		if (lang != that.lang)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = lang != null ? lang.hashCode() : 0;
		result = 31 * result + (country != null ? country.hashCode() : 0);
		return result;
	}
}
