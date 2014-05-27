package com.sopovs.moradanen.fan.domain.infra;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

import com.sopovs.moradanen.fan.domain.AbstractEntity;

@Entity
@Table(name = "TAG", uniqueConstraints = @UniqueConstraint(name = "TAG_VALUE_UK", columnNames = "VALUE"))
@Getter
@Setter
public class Tag extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private String value;

    @ManyToMany
    @JoinTable(name = "PAGE_TAG", foreignKey = @ForeignKey(name = "PAGE_TAG_TAG_FK"))
    private List<AbstractPage> pages;

    public Tag() {
    }

    public Tag(String value) {
        this.value = value;
    }

}
