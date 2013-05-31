package com.sopovs.moradanen.fan.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sopovs.moradanen.fan.domain.Country;
import com.sopovs.moradanen.fan.domain.NationalTeam;

@Controller
@RequestMapping("/edit")
public class EditController extends AbstractController {
    @PersistenceContext
    private EntityManager em;

    @RequestMapping(method = RequestMethod.GET)
    public String edit() {
        return "/edit/index";
    }

    @RequestMapping(value = "/ng/{ngTemplate}", method = RequestMethod.GET)
    public String getNgTemplate(@PathVariable String ngTemplate) {
        return "edit/ngtemplates/".concat(ngTemplate);
    }

    @RequestMapping(value = "/country", method = RequestMethod.POST)
    public void saveOrUpdateCountry(Country country) {
        em.persist(country);
    }

    @RequestMapping(value = "/nationalTeam", method = RequestMethod.POST)
    public void saveOfUpdateTeam(NationalTeam nationalTeam) {
        em.persist(nationalTeam);
    }

}
