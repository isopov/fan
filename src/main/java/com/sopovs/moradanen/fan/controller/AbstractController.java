package com.sopovs.moradanen.fan.controller;

import java.util.List;
import java.util.Locale;

import com.sopovs.moradanen.fan.domain.Contest;
import com.sopovs.moradanen.fan.domain.infra.Category;
import org.springframework.web.bind.annotation.ModelAttribute;
import sun.awt.ModalityListener;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public abstract class AbstractController {


    @PersistenceContext
    private EntityManager em;

	@ModelAttribute(value = "lang")
	public String lang(Locale locale) {
		return locale.getLanguage();
	}

    @ModelAttribute(value = "categories")
    public List<Category> categories(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> q = cb .createQuery(Category.class);
        Root<Category> from = q.from(Category.class);
        q.orderBy(cb.asc(from.get("priority")));
        return em.createQuery(q).getResultList();
    }

    protected final String previousUrl(String url, int showNum, int startFrom) {
        if (startFrom - showNum < 0) {
            return "";
        }
        return url + "?startFrom=" + (startFrom - showNum) + ((showNum != getDefaultShowNum()) ? "&showNum=" + showNum : "");
    }

    protected final String nextUrl(String url, int showNum, int startFrom, int countAll) {
        if (startFrom + showNum >= countAll) {
            return "";
        }
        return url + "?startFrom=" + (startFrom + showNum) + ((showNum != getDefaultShowNum()) ? "&showNum=" + showNum : "");
    }

    protected int getDefaultShowNum() {
        return 50;
    }

}
