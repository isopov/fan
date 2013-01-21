package com.sopovs.moradanen.fan.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Entity
@Table(name = "SEASON")
public class Season extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate start;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate end;

	@ManyToOne
	@ForeignKey(name = "SEASON_CONTEST_FK")
	private Contest contest;

	@OneToMany(mappedBy = "season")
	private List<Game> games;

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public List<Object> getTeams() {
		Set<Object> result = Sets.newHashSet();
		for (Game g : games) {
			for (TeamInGame t : g.getTeams()) {
				result.add(t.getTeam());
			}
		}
		return Lists.newArrayList(result);
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
}