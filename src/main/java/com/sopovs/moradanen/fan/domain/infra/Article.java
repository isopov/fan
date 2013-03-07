package com.sopovs.moradanen.fan.domain.infra;

import com.sopovs.moradanen.fan.domain.AbstractEntity;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "ARTICLE")
@ForeignKey(name = "ARTICLE_PAGE_FK")
public class Article extends AbstractPage {

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
