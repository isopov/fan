package com.sopovs.moradanen.fan.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DepartmentControllerTest extends AbstractControllerTest {

	@Test
	public void testList() throws Exception {
		this.mockMvc.perform(get("/department/list"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("List of departments")));
	}

	@Test
	public void testView() throws Exception {
		this.mockMvc.perform(get("/department/view/0"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(stringContainsInOrder(Arrays.asList("Department ID",
						"Department name",
						"List of employees"))));
	}

	@Test
	public void testNewDepartmentWithTooBigName() throws Exception {
		this.mockMvc.perform(post("/department/new").param("name",
				"TestDepartmentNameThatIsTooLongTestDepartmentNameThatIsTooLong"
						+ "TestDepartmentNameThatIsTooLongTestDepartmentNameThatIsTooLong"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("size must be between 0 and 30")));
	}

}