package com.sopovs.moradanen.fan.controller;

import com.sopovs.moradanen.fan.domain.Game;
import com.sopovs.moradanen.fan.domain.Season;
import com.sopovs.moradanen.fan.service.IDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping("/games")
public class GameController extends AbstractController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IDaoService service;

    @RequestMapping(value = "/list")
    public ModelAndView listGames() {
        return new ModelAndView("games/list", "games", service.lastGames(100));
    }

    @RequestMapping(value = "/view/{id}")
    public ModelAndView viewGame(@PathVariable UUID id,
                                  HttpServletResponse response) {
        Game game = em.find(Game.class, id);
        if (game == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ModelAndView("errors/404");
        } else {
            return new ModelAndView("games/view", "game", game);
        }
    }

}
