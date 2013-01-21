package com.sopovs.moradanen.fan.controller;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import com.sopovs.moradanen.fan.domain.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.service.IDaoService;

@Controller
@RequestMapping("/contest")
public class ContestController extends AbstractController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IDaoService service;

    @RequestMapping(value = "/games")
    public ModelAndView listGames() {
        return new ModelAndView("contest/games", "seasons", service.lastSeasons());
    }

    @RequestMapping(value = "/teams")
    public ModelAndView listTeams() {
        return new ModelAndView("contest/teams", "seasons", service.lastSeasons());
    }

    @RequestMapping(value = "/season/{id}", method = RequestMethod.GET)
    public ModelAndView viewClub(@PathVariable UUID id,
                                 HttpServletResponse response) {

        Season season = em.find(Season.class, id);
        if (season == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ModelAndView("errors/404");
        } else {
            return new ModelAndView("contest/season", "season", season);
        }
    }
}