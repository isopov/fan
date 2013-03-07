package com.sopovs.moradanen.fan.controller;

import com.sopovs.moradanen.fan.domain.infra.IndexPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

@Controller
public class IndexCotroller extends AbstractController {

    @PersistenceContext
    private EntityManager em;

    @ModelAttribute(value = "content")
    public IndexPage content() {
        CriteriaQuery<IndexPage> q = em.getCriteriaBuilder().createQuery(IndexPage.class);
        q.from(IndexPage.class);
        return em.createQuery(q).getSingleResult();
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
