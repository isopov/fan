package com.sopovs.moradanen.fan.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "I18N_CLUB", uniqueConstraints = @UniqueConstraint(name = "I18N_CLUB_NAME_UK", columnNames = "NAME"))
@Getter
@Setter
@NoArgsConstructor
public class I18nClub implements I18nDomain<Club> {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @ForeignKey(name = "I18N_CLUB_CLUB_FK")
    private Club club;

    @Id
    @Enumerated(EnumType.STRING)
    private Lang lang;

    private String name;

    public I18nClub(Lang lang, String name) {
        this.lang = lang;
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((club == null) ? 0 : club.hashCode());
        result = prime * result + ((lang == null) ? 0 : lang.hashCode());
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
        I18nClub other = (I18nClub) obj;
        if (club == null) {
            if (other.club != null)
                return false;
        } else if (!club.equals(other.club))
            return false;
        if (lang != other.lang)
            return false;
        return true;
    }

}
