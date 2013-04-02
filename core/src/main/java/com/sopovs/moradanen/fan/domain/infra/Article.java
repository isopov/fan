package com.sopovs.moradanen.fan.domain.infra;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "ARTICLE")
@ForeignKey(name = "ARTICLE_PAGE_FK")
public class Article extends AbstractPage {

    private static final long serialVersionUID = 1L;
    @ManyToOne
    @ForeignKey(name = "ARTICLE_CATEGORY_FK")
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
