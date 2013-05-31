package com.sopovs.moradanen.fan.controller.i18n;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sopovs.moradanen.fan.controller.AbstractControllerTest;

public abstract class AbstractI18nTest extends AbstractControllerTest {

    @Test
    public void testIndex() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(addLocale("/")))
                .andExpect(status().isOk());
    }

    @Test
    public void testListContestsWithGames() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(addLocale("/contest/games")))
                .andExpect(status().isOk());
    }

    @Test
    public void testListContestsWithTeams() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(addLocale("/contest/teams")))
                .andExpect(status().isOk());
    }

    private String addLocale(String url) {
        if (url.contains("?")) {
            return url + "&lang=" + getLocale();
        } else {
            return url + "?lang=" + getLocale();
        }
    }

    protected abstract String getLocale();

}
