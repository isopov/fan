package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "REFEREE")
@ForeignKey(name = "REFEREE_PERSON_FK")
public class Referee extends Person {
    private static final long serialVersionUID = 1L;

}
