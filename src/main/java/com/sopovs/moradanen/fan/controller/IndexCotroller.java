package com.sopovs.moradanen.fan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexCotroller {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

}
