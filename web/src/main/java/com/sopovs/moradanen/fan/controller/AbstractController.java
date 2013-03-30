package com.sopovs.moradanen.fan.controller;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Strings;
import com.sopovs.moradanen.fan.domain.infra.Category;

public abstract class AbstractController {

    @PersistenceContext
    private EntityManager em;

    @ModelAttribute(value = "lang")
    public String lang(Locale locale) {
        return locale.getLanguage();
    }

    @ModelAttribute(value = "languageChangeUrl")
    public String languageChangeUrl(HttpServletRequest req) {
        if (Strings.isNullOrEmpty(req.getParameter("lang"))) {

            String currentUrl = UrlUtils.buildFullRequestUrl(req);
            if (currentUrl.contains("?")) {
                return currentUrl.concat("&lang=");
            } else {
                return currentUrl.concat("?lang=");
            }
        } else {
            String queryString = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(req))
                    .replaceQueryParam("lang").build().getQuery();
            String currentUrl = UrlUtils.buildFullRequestUrl(req.getScheme(), req.getServerName(), req.getServerPort(),
                    req.getRequestURI(), queryString);
            if (currentUrl.contains("?")) {
                return currentUrl.concat("&lang=");
            } else {
                return currentUrl.concat("?lang=");
            }
        }
    }

    @ModelAttribute(value = "categories")
    public List<Category> categories() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> q = cb.createQuery(Category.class);
        Root<Category> from = q.from(Category.class);
        q.orderBy(cb.asc(from.get("priority")));
        return em.createQuery(q).getResultList();
    }

    protected final String previousUrl(String url, int showNum, int startFrom) {
        if (startFrom - showNum < 0) {
            return "";
        }
        return url + "?startFrom=" + (startFrom - showNum)
                + ((showNum != getDefaultShowNum()) ? "&showNum=" + showNum : "");
    }

    protected final String nextUrl(String url, int showNum, int startFrom, int countAll) {
        if (startFrom + showNum >= countAll) {
            return "";
        }
        return url + "?startFrom=" + (startFrom + showNum)
                + ((showNum != getDefaultShowNum()) ? "&showNum=" + showNum : "");
    }

    protected int getDefaultShowNum() {
        return 50;
    }

}
