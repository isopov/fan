package com.sopovs.moradanen.fan.domain.infra;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "INDEX_PAGE")
@ForeignKey(name = "INDEX_PAGE_PAGE_FK")
public class IndexPage extends AbstractPage {


}
