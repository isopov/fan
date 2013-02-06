package com.sopovs.moradanen.fan.service;

import com.sopovs.moradanen.fan.AbstractTransactionalServiceTest;
import com.sopovs.moradanen.fan.bootstrap.DbTestData;
import com.sopovs.moradanen.fan.domain.*;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.*;

public class DaoServiceTest extends AbstractTransactionalServiceTest {


    @Autowired
    private IDaoService service;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testTeamsPlayedWith() throws Exception {
        UUID blackId = service.findClubByName(DbTestData.BLACKBURN_NAME).getId();
        List<Team> teams = service.teamsPlayedWith(blackId, 0);
        assertTrue(teams.size() >= 1);
        assertEquals(service.findClubByName(DbTestData.FULHAM), teams.get(0));
        assertFalse(teams.contains(service.findClubByName(DbTestData.BLACKBURN_NAME)));

    }

    @Test
    public void testFindContestByName() throws Exception {
        assertEquals(DbTestData.BARCLAYS_PREMIER_LEAGUE, service.findContestByName(DbTestData.BARCLAYS_PREMIER_LEAGUE).getName());
    }

    @Test
    public void testListAllClubs() throws Exception {
        assertTrue(service.listAllClubs().size() > 0);
        service.listAllClubs().contains(service.findClubByName(DbTestData.BLACKBURN_NAME));
        service.listAllClubs().contains(service.findClubByName(DbTestData.FULHAM));
    }

    @Test
    public void testFindClubByName() throws Exception {
        Club blackburn = service.findClubByName(DbTestData.BLACKBURN_NAME);
        assertEquals(DbTestData.BLACKBURN_NAME, blackburn.getName());
        assertEquals(DbTestData.BLACKBURN_NAME, blackburn.getTitle("en"));

    }

    @Test
    public void testLastSeasonByClubName() throws Exception {
        Season season = service.lastSeasonByClubName(DbTestData.BLACKBURN_NAME);
        assertEquals(service.lastSeasons(), Arrays.asList(season));
        newSeason(season.getContest());
        assertEquals(season, service.lastSeasonByClubName(DbTestData.BLACKBURN_NAME));

    }

    @Test
    public void testGetGames() throws Exception {
        Team blackburn = service.findClubByName(DbTestData.BLACKBURN_NAME);
        Team fulham = service.findClubByName(DbTestData.FULHAM);
        List<Game> games = service.getGames(blackburn.getId(), fulham.getId());
        assertTrue(games.size() >= 1);
        for (Game game : games) {
            assertThat(game.getTeams(),hasItems(blackburn,fulham));
        }

    }

    @Test
    public void testLastSeasons() throws Exception {
        List<Season> seasons = service.lastSeasons();
        assertEquals(1, seasons.size());

        Season newSeason = newSeason(seasons.get(0).getContest());

        List<Season> newSeasons = service.lastSeasons();
        assertEquals(1, newSeasons.size());
        assertNotEquals(seasons, newSeasons);
        assertEquals(seasons.get(0).getContest(), newSeasons.get(0).getContest());
    }

    private Season newSeason(Contest contest) {
        Season newSeason = new Season();
        newSeason.setEndDate(LocalDate.now().plusYears(10));
        newSeason.setStartDate(LocalDate.now().plusYears(9));
        newSeason.setContest(contest);
        em.persist(newSeason);
        return newSeason;
    }
}
