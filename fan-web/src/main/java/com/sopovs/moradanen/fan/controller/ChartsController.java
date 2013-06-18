package com.sopovs.moradanen.fan.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sopovs.moradanen.fan.charts.DataTable;
import com.sopovs.moradanen.fan.service.IDaoService;

@Controller
@RequestMapping("/chart")
public class ChartsController extends AbstractController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IDaoService service;

    @RequestMapping(value = "/goals/{teamIds}/season/{seasonId}", method = RequestMethod.GET)
    @ResponseBody
    public String goalsChart(@PathVariable("teamIds") List<Long> teamIds,
            @PathVariable("teamIds") String teamIdsString, @PathVariable Long seasonId) {
        return "Foo";
    }

    @RequestMapping(value = "/data/goals/{teamIds}/season/{seasonId}", method = RequestMethod.GET)
    @ResponseBody
    public DataTable goalsChartData(@PathVariable List<Long> teamIds, @PathVariable Long seasonId) {
        return new DataTable(service.getCumulativeGoals(teamIds, seasonId));
    }

}
