package com.sopovs.moradanen.fan.domain.infra;


import com.sopovs.moradanen.fan.domain.AbstractEntity;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TAG", uniqueConstraints = @UniqueConstraint(name = "TAG_VALUE_UK", columnNames = "VALUE"))
public class Tag extends AbstractEntity {

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
