package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "PLAYER")
@ForeignKey(name = "PLAYER_PERSON_FK")
public class Player extends Person {
	private static final long serialVersionUID = 1L;

}
