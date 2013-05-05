package com.sopovs.moradanen.fan.domain;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
// Unique constraint maybe realeased when such data will be inserted, but
// probably it would be extended to another field maybe not yet present
@Table(name = "PERSON", uniqueConstraints =
        @UniqueConstraint(name = "PERSON_NAMES_UK", columnNames = { "FIRST_NAME", "LAST_NAME", "MIDDLE_NAME" }))
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
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

    public Person(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
