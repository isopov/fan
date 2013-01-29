package com.sopovs.moradanen.fan.controller;

import com.sopovs.moradanen.fan.domain.Game;
import com.sopovs.moradanen.fan.domain.Player;
import com.sopovs.moradanen.fan.service.IDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping("/players")
public class PlayerController extends AbstractController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IDaoService service;

    @RequestMapping(value = "/list")
    public ModelAndView listGames() {
        return new ModelAndView("players/list", "players", service.listAllPlayers());
    }

    @RequestMapping(value = "/view/{id}")
    public ModelAndView viewPlayer(@PathVariable UUID id,
                                   HttpServletResponse response) {
        Player player = em.find(Player.class, id);
        if (player == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ModelAndView("errors/404");
        } else {
            return new ModelAndView("players/view", "player", player).addObject("lastGames", service.lastGamesForPlayer(id, 30));
        }
    }

}
