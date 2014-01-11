package com.sopovs.moradanen.fan.bootstrap;

import static com.sopovs.moradanen.fan.bootstrap.DbTestData.BARCLAYS_PREMIER_LEAGUE;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sopovs.moradanen.fan.service.IDaoService;

/**
 * Importer for data from http://www.football-data.co.uk/data.php
 */

public class FootballData {

    private final Logger logger = LoggerFactory.getLogger(FootballData.class);

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private IDaoService service;
    @Autowired
    private IFootballDataImporter importer;

    public void importFootballData() throws InterruptedException {
        logger.info("Importing football data without details");

        importer.importDataFromFile(FootballData.class.getResourceAsStream("/1011-E0.csv"), BARCLAYS_PREMIER_LEAGUE);
        importer.importDataFromFile(FootballData.class.getResourceAsStream("/0910-E0.csv"), BARCLAYS_PREMIER_LEAGUE);

        // importer.importDataFromFile(FootballData.class.getResourceAsStream("/1112-E0.csv"),
        // BARCLAYS_PREMIER_LEAGUE);
        // importer.importDataFromFile(FootballData.class.getResourceAsStream("/1112-E1.csv"),
        // ENGLAND_LEAGUE_CHAMPIONSHIP);
        // importer.importDataFromFile(FootballData.class.getResourceAsStream("/1112-E2.csv"),
        // ENGLAND_LEAGUE_ONE);
        // importer.importDataFromFile(FootballData.class.getResourceAsStream("/1112-E3.csv"),
        // ENGLAND_LEAGUE_TWO);
        // importer.importDataFromFile(FootballData.class.getResourceAsStream("/1112-EC.csv"),
        // ENGLAND_FOOTBALL_CONFERENCE);
        //
        // importer.importDataFromFile(FootballData.class.getResourceAsStream("/1213-E0.csv"),
        // BARCLAYS_PREMIER_LEAGUE);
        // importer.importDataFromFile(FootballData.class.getResourceAsStream("/1213-E1.csv"),
        // ENGLAND_LEAGUE_CHAMPIONSHIP);
        // importer.importDataFromFile(FootballData.class.getResourceAsStream("/1213-E2.csv"),
        // ENGLAND_LEAGUE_ONE);
        // importer.importDataFromFile(FootballData.class.getResourceAsStream("/1213-E3.csv"),
        // ENGLAND_LEAGUE_TWO);
        // importer.importDataFromFile(FootballData.class.getResourceAsStream("/1213-EC.csv"),
        // ENGLAND_FOOTBALL_CONFERENCE);

    }

}
