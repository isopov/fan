package com.sopovs.moradanen.fan.controller;

import java.util.Locale;

import org.springframework.web.bind.annotation.ModelAttribute;

public class AbstractController {

	@ModelAttribute(value = "lang")
	public String lang(Locale locale) {
		return locale.getLanguage();
	}

}
