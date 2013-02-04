package com.sopovs.moradanen.fan.controller;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.service.IDaoService;

@Controller
@RequestMapping("/club")
public class ClubController extends AbstractController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IDaoService service;

    @RequestMapping(value = "/list")
    public ModelAndView listClubs(@ModelAttribute("club") Club club) {
        return new ModelAndView("club/list", "clubs", service.listAllClubs());
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView viewClub(@PathVariable UUID id, HttpServletResponse response) {
        Club club  = em.find(Club.class, id);

        if (club == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ModelAndView("errors/404");
        } else {
            return new ModelAndView("club/view", "club", club)
                    .addObject("lastGames", service.lastGamesForTeam(id, 100))
                    .addObject("teamsPlayedWith",service.teamsPlayedWith(id,100));
        }
    }
}
