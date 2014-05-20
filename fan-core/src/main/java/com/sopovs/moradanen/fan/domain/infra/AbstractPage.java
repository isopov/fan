package com.sopovs.moradanen.fan.domain.infra;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;
import org.joda.time.LocalDate;

import com.sopovs.moradanen.fan.domain.DefaultI18nedDomain;
import com.sopovs.moradanen.fan.domain.Lang;

@Entity
@Table(name = "PAGE", uniqueConstraints = @UniqueConstraint(name = "PAGE_ADDRESS_UK", columnNames = { "SHORT_TITLE",
        "PUB_DATE" }))
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class AbstractPage extends DefaultI18nedDomain<I18nPage> {

    private static final long serialVersionUID = 1L;
    private String text;
    private String title;

    @Column(name = "SHORT_TITLE")
    private String shortTitle;

    @Column(name = "PUB_DATE")
    private LocalDate pubDate;

    @ManyToMany(mappedBy = "pages")
    @ForeignKey(name = "PAGE_TAG_PAGE_FK")
    private List<Tag> tags;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    @MapKey(name = "lang")
    private Map<Lang, I18nPage> i18ns;

    public AbstractPage() {
    }

    public AbstractPage(User author, LocalDate pubDate, String shortTitle, String title, String text) {
        this.author = author;
        this.pubDate = pubDate;
        this.shortTitle = shortTitle;
        this.title = title;
        this.text = text;
    }

    public AbstractPage(String title, String shortTitle, LocalDate pubDate, User author) {
        this.title = title;
        this.shortTitle = shortTitle;
        this.pubDate = pubDate;
        this.author = author;
    }

    @ManyToOne
    @ForeignKey(name = "PAGE_AUTHOR_FK")
    private User author;

}
