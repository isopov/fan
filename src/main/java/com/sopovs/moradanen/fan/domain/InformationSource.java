package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "INFORMATION_SOURCE")
public class InformationSource extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@ForeignKey(name = "INFORMATION_SOURCE_GROUP_FK")
	private InformationSources group;

	private String url;

	@Enumerated(EnumType.STRING)
	private License license;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InformationSources getGroup() {
		return group;
	}

	public void setGroup(InformationSources group) {
		this.group = group;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

}
