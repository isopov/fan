package com.sopovs.moradanen.fan.domain.infra;

import com.sopovs.moradanen.fan.domain.I18nDomain;
import com.sopovs.moradanen.fan.domain.Lang;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

@Entity
@Table(name = "I18N_PAGE")
public class I18nPage implements I18nDomain<AbstractPage> {

    @Id
    @ManyToOne
    @ForeignKey(name = "I18N_PAGE_PAGE_FK")
    private AbstractPage page;

    @Id
    @Enumerated(EnumType.STRING)
    private Lang lang;

    private String text;
    private String title;


    public I18nPage() {
    }

    public I18nPage(AbstractPage page, Lang lang, String title, String text) {
        this.page = page;
        this.lang = lang;
        this.text = text;
        this.title = title;
    }

    @Override
    public Lang getLang() {
        return lang;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }


    public AbstractPage getPage() {
        return page;
    }

    public void setPage(AbstractPage page) {
        this.page = page;
    }
}
