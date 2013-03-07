package com.sopovs.moradanen.fan.domain.infra;

import com.sopovs.moradanen.fan.domain.DefaultI18nedDomain;
import com.sopovs.moradanen.fan.domain.I18nPerson;
import com.sopovs.moradanen.fan.domain.Lang;
import org.hibernate.annotations.ForeignKey;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "PAGE", uniqueConstraints = @UniqueConstraint(name = "PAGE_ADDRESS_UK", columnNames = {"SHORT_TITLE", "PUB_DATE"}))
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractPage extends DefaultI18nedDomain<I18nPage> {

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public Map<Lang, I18nPage> getI18ns() {
        return i18ns;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setI18ns(Map<Lang, I18nPage> i18ns) {
        this.i18ns = i18ns;
    }
}
