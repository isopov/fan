package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

//FIFA, UEFA, AFC etc.
@Entity
@Table(name = "ORGANIZATION", uniqueConstraints = @UniqueConstraint(name = "ORGANIZATION_NAME_UK", columnNames = "NAME"))
@ForeignKey(name = "ORGANIZATION_CONTEST_HOLDER_FK")
@Getter
@Setter
public class Organization extends ContestHolder {
    private static final long serialVersionUID = 1L;

    private String name;

}
