package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@ForeignKey(name = "COACH_PERSON_FK")
@Table(name = "COACH")
public class Coach extends Person {

    private static final long serialVersionUID = 1L;

}