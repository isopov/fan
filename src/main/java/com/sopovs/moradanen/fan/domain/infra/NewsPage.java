package com.sopovs.moradanen.fan.domain.infra;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="NEWS_PAGE")
@ForeignKey(name = "NEWS_PAGE_PAGE_FK")
public class NewsPage extends AbstractPage{

}
