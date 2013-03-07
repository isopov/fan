package com.sopovs.moradanen.fan.domain.infra;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "NEWS_PAGE")
@ForeignKey(name = "NEWS_PAGE_PAGE_FK")
public class NewsPage extends AbstractPage {

    private static final long serialVersionUID = 1L;

}
