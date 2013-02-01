package com.sopovs.moradanen.fan.controller;

import com.google.common.collect.Lists;
import com.sopovs.moradanen.fan.domain.Player;
import com.sopovs.moradanen.fan.service.IDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/players")
public class PlayerController extends AbstractController {
    private static final String DEFAULT_SHOWNUM = "30";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IDaoService service;

    @RequestMapping(value = "/list")
    public ModelAndView listPlayers() {
        return new ModelAndView("players/list", "players", service.listAllPlayers());
    }

    @RequestMapping(value = "/view/{id}")
    public ModelAndView viewPlayer(@PathVariable UUID id,
                                   HttpServletResponse response,
                                   @RequestParam(defaultValue = "0", required = false) int startFrom,
                                   @RequestParam(defaultValue = DEFAULT_SHOWNUM, required = false) int showNum) {
        Player player = em.find(Player.class, id);
        if (player == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ModelAndView("errors/404");
        } else {
            return new ModelAndView("players/view", "player", player)
                    .addObject("lastGames", service.lastGamesForPlayer(id, showNum, startFrom))
                    .addObject("previousUrl", previousUrl("/view/" + id.toString(), showNum, startFrom))
                    .addObject("nextUrl", nextUrl("/view/" + id.toString(), showNum, startFrom, service.countGamesForPlayer(id)));
        }
    }


    @Override
    protected int getDefaultShowNum() {
        return Integer.valueOf(DEFAULT_SHOWNUM);
    }
}
