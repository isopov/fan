package com.sopovs.moradanen.fan.domain.infra;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sopovs.moradanen.fan.domain.AbstractEntity;

@Entity
@Table(name = "ARTICLE_CATEGORY", uniqueConstraints = @UniqueConstraint(name = "ARTICLE_CATEGORY_PRIORITY_UK", columnNames = "PRIORITY"))
public class Category extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "category")
    private List<Article> articles;

    @Column(nullable = false)
    private Integer priority;

    private String name;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
