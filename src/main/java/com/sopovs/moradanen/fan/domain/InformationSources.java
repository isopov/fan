package com.sopovs.moradanen.fan.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "INFORMATION_SOURCES")
public class InformationSources extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "group")
	private List<InformationSource> sources;

	public List<InformationSource> getSources() {
		return sources;
	}

	public void setSources(List<InformationSource> sources) {
		this.sources = sources;
	}
}
