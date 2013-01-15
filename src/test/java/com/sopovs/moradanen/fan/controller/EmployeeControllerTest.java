package com.sopovs.moradanen.fan.controller;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class EmployeeControllerTest extends AbstractControllerTest {

	@Test
	public void testList() throws Exception {
		this.mockMvc.perform(get("/employee/list"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("First Name")));
	}

	@Test
	public void testListExcel() throws Exception {
		this.mockMvc.perform(get("/employee/list/excel"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/vnd.ms-excel"))
				.andExpect(header().string("Content-Disposition", "attachment; filename=\"employee-list.xls\""));
	}

	@Test
	public void testView() throws Exception {
		this.mockMvc.perform(get("/employee/view/0"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("First Name")));
	}

	@Test
	public void testSearchForm() throws Exception {
		this.mockMvc
				.perform(get("/employee/search"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(
						content().string(
								stringContainsInOrder(Arrays.asList("<h1>Search for employees</h1>",
										"<form method=\"POST\">",
										"<input type=\"text\" name=\"searchString\" />",
										"<input type=\"submit\" />",
										"</form>"))));
	}

	@Test
	public void testSearchNothing() throws Exception {
		this.mockMvc
				.perform(post("/employee/search").param("searchString", "Should not find anyone"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(
						content().string(
								stringContainsInOrder(Arrays.asList("<h1>Search for employees</h1>",
										"<form method=\"POST\">",
										"<input type=\"text\" name=\"searchString\" />",
										"<input type=\"submit\" />",
										"</form>"))))
				.andExpect(content().string(not(containsString("<a href=\"/employee/view/"))));
	}

	@Test
	public void testSearchEverybody() throws Exception {
		this.mockMvc
				.perform(post("/employee/search").param("searchString", "*"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(
						content().string(
								stringContainsInOrder(Arrays.asList("<h1>Search for employees</h1>",
										"<form method=\"POST\">",
										"<input type=\"text\" name=\"searchString\" />",
										"<input type=\"submit\" />",
										"</form>"))))
				.andExpect(content().string(containsString("<a href=\"/employee/view/")));
	}

}