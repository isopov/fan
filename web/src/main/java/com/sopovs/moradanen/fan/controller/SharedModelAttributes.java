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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Strings;
import com.sopovs.moradanen.fan.domain.infra.Category;

@ControllerAdvice
public class SharedModelAttributes {
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
}
