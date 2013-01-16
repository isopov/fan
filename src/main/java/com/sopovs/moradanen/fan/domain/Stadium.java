package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "STADIUM")
public class Stadium extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String name;
	@ManyToOne
	@ForeignKey(name = "STADIUM_COUNTRY_FK")
	private Country country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
