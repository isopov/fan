package com.sopovs.moradanen.fan.controller;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sopovs.moradanen.fan.AbstractTransactionalServiceTest;
import com.sopovs.moradanen.fan.WebApplicationConfiguration;

@WebAppConfiguration
@ContextConfiguration(classes = WebApplicationConfiguration.class)
public abstract class AbstractControllerTest extends AbstractTransactionalServiceTest {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

}