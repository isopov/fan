package com.sopovs.moradanen.fan.domain.infra;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "INDEX_PAGE")
@ForeignKey(name = "INDEX_PAGE_PAGE_FK")
public class IndexPage extends AbstractPage {
    private static final long serialVersionUID = 1L;

}
