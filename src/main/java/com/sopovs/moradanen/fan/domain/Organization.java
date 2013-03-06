package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;

//FIFA, UEFA, AFC etc.
@Entity
@Table(name = "ORGANIZATION", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
@ForeignKey(name = "ORGANIZATION_CONTEST_HOLDER_FK")
public class Organization extends ContestHolder {
    private static final long serialVersionUID = 1L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
