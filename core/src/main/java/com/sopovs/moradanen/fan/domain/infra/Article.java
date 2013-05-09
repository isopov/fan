package com.sopovs.moradanen.fan.domain.infra;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "ARTICLE")
@ForeignKey(name = "ARTICLE_PAGE_FK")
@Getter
@Setter
public class Article extends AbstractPage {

    private static final long serialVersionUID = 1L;
    @ManyToOne
    @ForeignKey(name = "ARTICLE_CATEGORY_FK")
    private Category category;

}
