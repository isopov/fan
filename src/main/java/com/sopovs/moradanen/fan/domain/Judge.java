package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "JUDGE")
@ForeignKey(name = "JUDGE_PERSON_FK")
public class Judge extends Person {
	private static final long serialVersionUID = 1L;

}
