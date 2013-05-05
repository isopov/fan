package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CONTEST_HOLDER")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class ContestHolder extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "holder")
    public List<Contest> contests;

}
