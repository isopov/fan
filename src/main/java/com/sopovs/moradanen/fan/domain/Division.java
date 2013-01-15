package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

@Entity
public class Division extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@ForeignKey(name = "DIVISION_COUNTRY_FK")
	private Country country;
	@Enumerated(EnumType.STRING)
	private DivisionType positition;
	private String name;

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

	public DivisionType getPositition() {
		return positition;
	}

	public void setPositition(DivisionType positition) {
		this.positition = positition;
	}

}
