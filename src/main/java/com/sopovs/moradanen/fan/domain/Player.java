package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;

import org.hibernate.annotations.ForeignKey;

@Entity
@ForeignKey(name = "PLAYER_PERSON_FK")
public class Player extends Person {
	private static final long serialVersionUID = 1L;

}
