package com.sopovs.moradanen.fan.controller;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sopovs.moradanen.fan.bootstrap.DbTestData;
import com.sopovs.moradanen.fan.service.IDaoService;

public class ContestControllerTest extends AbstractControllerTest {

    @Autowired
    private IDaoService daoService;

    @Test
    public void testListGames() throws Exception {
        this.mockMvc.perform(get("/contest/games")).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(stringContainsInOrder(Arrays.asList("DateTime", "Host", "Guest"))));
    }

    @Test
    public void testViewSeason() throws Exception {
        daoService.lastSeasonByClubName(DbTestData.BLACKBURN_NAME).getId();
        this.mockMvc
                .perform(
                        get("/contest/season/"
                                + daoService.lastSeasonByClubName(DbTestData.BLACKBURN_NAME).getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(
                        content().string(
                                allOf(containsString(DbTestData.BLACKBURN_NAME), containsString(DbTestData.FULHAM))));
    }

    @Test
    public void testListTeams() throws Exception {
        this.mockMvc
                .perform(get("/contest/teams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(
                        content().string(
                                allOf(containsString("Season"), containsString(DbTestData.BLACKBURN_NAME),
                                        containsString(DbTestData.FULHAM))));
    }
}