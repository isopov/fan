package com.sopovs.moradanen.fan.controller;

import java.util.Locale;

import org.springframework.web.bind.annotation.ModelAttribute;

public class AbstractController {

	@ModelAttribute(value = "lang")
	public String lang(Locale locale) {
		return locale.getLanguage();
	}

    protected final String previousUrl(String url, int showNum, int startFrom) {
        if (startFrom - showNum <= 0) {
            return "";
        }
        return url + "?startFrom=" + (startFrom - showNum) + ((showNum != getDefaultShowNum()) ? "&showNum=" + showNum : "");
    }

    protected final String nextUrl(String url, int showNum, int startFrom, int countAll) {
        if (startFrom + showNum >= countAll) {
            return "";
        }
        return url + "?startFrom=" + (startFrom + showNum) + ((showNum != getDefaultShowNum()) ? "&showNum=" + showNum : "");
    }

    protected int getDefaultShowNum() {
        return 100;
    }

}
