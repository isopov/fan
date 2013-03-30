package com.sopovs.moradanen.fan.controller;

import static com.sopovs.moradanen.fan.bootstrap.DbTestData.BLACKBURN_NAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Game;
import com.sopovs.moradanen.fan.service.IDaoService;

public class ClubControllerTest extends AbstractControllerTest {

    @Autowired
    private IDaoService daoService;

    @Test
    public void testView() throws Exception {
        Club blackburn = daoService.findClubByName(BLACKBURN_NAME);
        this.mockMvc.perform(get("/club/view/" + blackburn.getId().toString())).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    public void testList() throws Exception {
        this.mockMvc.perform(get("/club/list")).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    public void testDerby() throws Exception {
        Game game = daoService.lastGames(1).get(0);
        this.mockMvc
                .perform(
                        get("/club/derby/" + game.getHost().getTeam().getId().toString() + "/vs/"
                                + game.getGuest().getTeam().getId().toString())).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }
}