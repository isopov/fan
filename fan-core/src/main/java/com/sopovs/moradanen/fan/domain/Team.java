package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "TEAM")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Team extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    public abstract String getTitle();

    public abstract String getTitle(String lang);
}
