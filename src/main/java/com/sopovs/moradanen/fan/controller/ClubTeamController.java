package com.sopovs.moradanen.fan.controller;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Strings;
import com.sopovs.moradanen.fan.domain.ClubTeam;
import com.sopovs.moradanen.fan.service.IDaoService;

@Controller
@RequestMapping("/club/team")
public class ClubTeamController {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IDaoService service;

	@ModelAttribute("clubTeam")
	public ClubTeam getClubTeam(@RequestParam(required = false) UUID id, @RequestParam(required = false) String name) {
		if (id != null) {
			return em.find(ClubTeam.class, id);
		} else if (Strings.isNullOrEmpty(name)) {
			return service.getByClubTeamName(name);
		} else {
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String viewClubTeam(@ModelAttribute("clubTeam") ClubTeam clubTeam) {
		if (clubTeam == null) {
			return "404";
		} else {
			return "clubTeam";
		}
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public static class BadRequestException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

}
