package com.sopovs.moradanen.fan.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sopovs.moradanen.fan.domain.Player;
import com.sopovs.moradanen.fan.service.IDaoService;

public class PlayerControllerTest extends AbstractControllerTest {

    @Autowired
    private IDaoService service;

    @Test
    public void testList() throws Exception {
        this.mockMvc.perform(get("/players/list")).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    public void testView() throws Exception {
        Player player = service.listAllPlayers().get(0);
        this.mockMvc.perform(get("/players/view/" + player.getId().toString())).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }
}