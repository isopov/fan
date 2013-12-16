package com.sopovs.moradanen.fan.controller;

import static com.sopovs.moradanen.fan.bootstrap.DbTestData.BLACKBURN_NAME;
import static com.sopovs.moradanen.fan.bootstrap.DbTestData.FULHAM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Season;
import com.sopovs.moradanen.fan.service.IDaoService;

public class ChartsControllerTest extends AbstractControllerTest {

    @Autowired
    private IDaoService daoService;

    @Test
    public void testGoalsChartData() throws Exception {
        Club blackburn = daoService.findClubByName(BLACKBURN_NAME);
        Club fulham = daoService.findClubByName(FULHAM);
        Season season = daoService.lastSeasonByClubName(FULHAM);
        this.mockMvc.perform(get("/chart/data/goals/" + blackburn.getId() + "," + fulham.getId()
                + "/season/" + season.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("cols").isArray())
                .andExpect(jsonPath("cols[0].id").value("gameId"))
                .andExpect(jsonPath("cols[0].label").value("Game Id"))
                .andExpect(jsonPath("cols[0].type").value("string"))
                .andExpect(jsonPath("cols[1].id").value("team-1"))
                .andExpect(jsonPath("cols[1].label").value("Blackburn"))
                .andExpect(jsonPath("cols[1].type").value("string"))
                .andExpect(jsonPath("cols[2].id").value("team-2"))
                .andExpect(jsonPath("cols[2].label").value("Fulham"))
                .andExpect(jsonPath("cols[2].type").value("string"))
                .andExpect(jsonPath("rows").isArray())
                .andExpect(jsonPath("rows[0].c").isArray())
                .andExpect(jsonPath("rows[0].c[0].v").value("1"))
                .andExpect(jsonPath("rows[0].c[1].v").value("3"))
                .andExpect(jsonPath("rows[0].c[2].v").value("1"));
    }
}
