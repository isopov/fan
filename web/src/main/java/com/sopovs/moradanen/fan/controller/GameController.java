package com.sopovs.moradanen.fan.controller;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sopovs.moradanen.fan.domain.Game;
import com.sopovs.moradanen.fan.service.IDaoService;

@Controller
@RequestMapping("/games")
public class GameController extends AbstractController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IDaoService service;

    @RequestMapping(value = "/list")
    public ModelAndView listGames(@RequestParam(defaultValue = "0", required = false) int startFrom,
            @RequestParam(defaultValue = "50", required = false) int showNum) {
        return new ModelAndView("games/list", "games", service.lastGames(showNum, startFrom)).addObject("previousUrl",
                previousUrl("/games/list", showNum, startFrom)).addObject("nextUrl",
                nextUrl("/games/list", showNum, startFrom, service.countGames()));
    }

    @RequestMapping(value = "/view/{id}")
    public ModelAndView viewGame(@PathVariable UUID id, HttpServletResponse response) {
        Game game = em.find(Game.class, id);
        if (game == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ModelAndView("errors/404");
        } else {
            return new ModelAndView("games/view", "game", game);
        }
    }
}
