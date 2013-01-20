package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "I18N_STADIUM")
public class I18nStadium implements I18nDomain<Stadium> {

	private static final long serialVersionUID = 1L;
	@Id
	@Enumerated(EnumType.STRING)
	private Lang lang;
	@Id
	@ManyToOne
	@ForeignKey(name = "I18N_STADIUM_STADIUM_FK")
	private Stadium stadium;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}

	@Override
	public Lang getLang() {
		return lang;
	}

	public void setLang(Lang lang) {
		this.lang = lang;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		I18nStadium that = (I18nStadium) o;

		if (lang != that.lang)
			return false;
		if (stadium != null ? !stadium.equals(that.stadium)
				: that.stadium != null)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = lang != null ? lang.hashCode() : 0;
		result = 31 * result + (stadium != null ? stadium.hashCode() : 0);
		return result;
	}
}
