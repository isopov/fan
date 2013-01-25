package com.sopovs.moradanen.fan.controller;

import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.service.IDaoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sopovs.moradanen.fan.bootstrap.DbTestData.BLACKBURN_NAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GameControllerTest extends AbstractControllerTest {

	@Autowired
	private IDaoService daoService;


    @Test
    public void testList() throws Exception {

        this.mockMvc.perform(get("/games/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

	@Test
	public void testView() throws Exception {
		Club blackburn = daoService.findClubByName(BLACKBURN_NAME);
		this.mockMvc.perform(get("/club/view?id=" + blackburn.getId().toString()))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"));
	}
}