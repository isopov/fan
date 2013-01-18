package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "CONTEST")
public class Contest extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@ForeignKey(name = "CONTEST_CONTEST_HOLDER_FK")
	private ContestHolder holder;
	@Enumerated(EnumType.STRING)
	private ContestType position;
	private String name;

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

}
