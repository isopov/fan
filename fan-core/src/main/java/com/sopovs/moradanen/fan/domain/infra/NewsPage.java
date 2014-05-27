package com.sopovs.moradanen.fan.domain.infra;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.SecondaryTable;

@Entity
@SecondaryTable(name = "NEWS_PAGE", foreignKey = @ForeignKey(name = "NEWS_PAGE_PAGE_FK"))
public class NewsPage extends AbstractPage {

    private static final long serialVersionUID = 1L;

}
