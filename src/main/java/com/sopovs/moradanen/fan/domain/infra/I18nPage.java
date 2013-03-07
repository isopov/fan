package com.sopovs.moradanen.fan.domain.infra;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.sopovs.moradanen.fan.domain.I18nDomain;
import com.sopovs.moradanen.fan.domain.Lang;

@Entity
@Table(name = "I18N_PAGE")
public class I18nPage implements I18nDomain<AbstractPage> {

    private static final long serialVersionUID = 1L;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lang == null) ? 0 : lang.hashCode());
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        I18nPage other = (I18nPage) obj;
        if (lang != other.lang)
            return false;
        if (page == null) {
            if (other.page != null)
                return false;
        } else if (!page.equals(other.page))
            return false;
        return true;
    }

}
