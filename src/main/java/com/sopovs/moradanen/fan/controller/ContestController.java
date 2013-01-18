package com.sopovs.moradanen.fan.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Contest;
import com.sopovs.moradanen.fan.domain.Season;
import com.sopovs.moradanen.fan.service.IDaoService;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.util.calendar.CalendarUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/contest")
public class ContestController extends AbstractController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IDaoService service;

    @RequestMapping(value = "/list")
    public ModelAndView listClubs(@ModelAttribute("club") Club club) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        ModelAndView modelAndView = new ModelAndView("contest/list");
        {

            CriteriaQuery<Contest> q = cb.createQuery(Contest.class);
            q.from(Contest.class);
            em.createQuery(q);
            modelAndView.addObject("contests", em.createQuery(q).getResultList());
        }
        {
            List<Season> seasons = service.lastSeasons();
            HashMap<Contest, Season> seasonsMap = Maps.newHashMapWithExpectedSize(seasons.size());
            for (Season s : seasons) {

                Season previous = seasonsMap.put(s.getContest(), s);
                Preconditions.checkState(previous == null);
            }
            modelAndView.addObject("seasons", seasonsMap);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView viewClub(@RequestParam(required = false) UUID id,
                                 @RequestParam(required = false) String clubName, HttpServletResponse response) {
        Club club = null;
        if (id != null) {
            club = em.find(Club.class, id);
        } else if (!Strings.isNullOrEmpty(clubName)) {
            club = service.findClubByName(clubName);
        }

        if (club == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ModelAndView("errors/404");
        } else {
            return new ModelAndView("club/view", "club", club);
        }
    }
}
