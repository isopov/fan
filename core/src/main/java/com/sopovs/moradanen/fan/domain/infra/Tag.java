package com.sopovs.moradanen.fan.domain.infra;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;

import com.sopovs.moradanen.fan.domain.AbstractEntity;

@Entity
@Table(name = "TAG", uniqueConstraints = @UniqueConstraint(name = "TAG_VALUE_UK", columnNames = "VALUE"))
public class Tag extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private String value;

    @ManyToMany
    @JoinTable(name = "PAGE_TAG")
    @ForeignKey(name = "PAGE_TAG_TAG_FK")
    private List<AbstractPage> pages;

    public Tag() {
    }

    public Tag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<AbstractPage> getPages() {
        return pages;
    }

    public void setPages(List<AbstractPage> pages) {
        this.pages = pages;
    }
}
