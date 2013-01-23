package com.sopovs.moradanen.fan.domain;

import java.util.Map;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
//Unique constraint maybe realeased when such data will be inserted, but probably it would be extended to another field maybe not yet present
@Table(name = "PERSON",uniqueConstraints = @UniqueConstraint(name = "PERSON_NAMES_UK",columnNames = {"FIRST_NAME","LAST_NAME","MIDDLE_NAME"}))
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends DefaultI18nedDomain<I18nPerson> {
	private static final long serialVersionUID = 1L;
    @Column(name = "FIRST_NAME")
	private String firstName;
    @Column(name = "LAST_NAME")
	private String lastName;
    @Column(name = "MIDDLE_NAME")
	private String middleName;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate dateOfBirth;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate dateOfDeath;

	@OneToMany(mappedBy = "person")
	@MapKey(name = "lang")
	private Map<Lang, I18nPerson> i18ns;

	public Person() {
	}

	public Person(String firstName, String lastName, String middleName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(LocalDate dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	@Override
	public Map<Lang, I18nPerson> getI18ns() {
		return i18ns;
	}
}
