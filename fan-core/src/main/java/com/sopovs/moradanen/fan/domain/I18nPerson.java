package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "I18N_PERSON")
@Getter
@Setter
public class I18nPerson implements I18nDomain<Person> {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @ForeignKey(name = "I18N_PERSON_PERSON_FK")
    private Person person;

    @Id
    @Enumerated(EnumType.STRING)
    private Lang lang;

    private String firstName;
    private String lastName;
    private String middleName;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lang == null) ? 0 : lang.hashCode());
        result = prime * result + ((person == null) ? 0 : person.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        I18nPerson other = (I18nPerson) obj;
        if (lang != other.lang)
            return false;
        if (person == null) {
            if (other.person != null)
                return false;
        } else if (!person.equals(other.person))
            return false;
        return true;
    }

}
