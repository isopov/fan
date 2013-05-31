package com.sopovs.moradanen.fan.domain.infra;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

import com.sopovs.moradanen.fan.domain.AbstractEntity;

@Entity
@Table(name = "ARTICLE_CATEGORY", uniqueConstraints = @UniqueConstraint(name = "ARTICLE_CATEGORY_PRIORITY_UK", columnNames = "PRIORITY"))
@Getter
@Setter
public class Category extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "category")
    private List<Article> articles;

    @Column(nullable = false)
    private Integer priority;

    private String name;

}
