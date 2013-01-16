package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CONTEST_HOLDER")
@Inheritance(strategy = InheritanceType.JOINED)
public class ContestHolder extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "holder")
	public List<Contest> contests;

	public List<Contest> getContests() {
		return contests;
	}

	public void setContests(List<Contest> contests) {
		this.contests = contests;
	}

}
