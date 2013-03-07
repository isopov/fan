package com.sopovs.moradanen.fan.domain.infra;

import com.sopovs.moradanen.fan.domain.AbstractEntity;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

@Entity
@Table(name = "ARTICLE_CATEGORY", uniqueConstraints = @UniqueConstraint(name = "ARTICLE_CATEGORY_PRIORITY_UK", columnNames = "PRIORITY"))
public class Category extends AbstractEntity {

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
