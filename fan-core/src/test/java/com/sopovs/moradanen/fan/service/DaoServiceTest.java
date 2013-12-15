package com.sopovs.moradanen.fan.service;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sopovs.moradanen.fan.CoreApplicationConfiguration;
import com.sopovs.moradanen.fan.bootstrap.DbTestData;
import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Contest;
import com.sopovs.moradanen.fan.domain.Game;
import com.sopovs.moradanen.fan.domain.Season;
import com.sopovs.moradanen.fan.domain.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreApplicationConfiguration.class)
@Transactional
public class DaoServiceTest {

    @Autowired
    private IDaoService service;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testTeamsPlayedWith() throws Exception {
        Long blackId = service.findClubByName(DbTestData.BLACKBURN_NAME).getId();
        List<Team> teams = service.teamsPlayedWith(blackId, 0);
        assertTrue(teams.size() >= 1);
        assertEquals(service.findClubByName(DbTestData.FULHAM), teams.get(0));
        assertFalse(teams.contains(service.findClubByName(DbTestData.BLACKBURN_NAME)));

    }

    @Test
    public void testFindContestByName() throws Exception {
        assertEquals(DbTestData.BARCLAYS_PREMIER_LEAGUE, service.findContestByName(DbTestData.BARCLAYS_PREMIER_LEAGUE)
                .getName());
    }

    @Test
    public void testListAllClubs() throws Exception {
        ArrayList<Club> clubs = newArrayList(service.listAllClubs());
        assertTrue(clubs.size() > 0);
        clubs.contains(service.findClubByName(DbTestData.BLACKBURN_NAME));
        clubs.contains(service.findClubByName(DbTestData.FULHAM));
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
            assertThat(game.getTeams(), hasItems(blackburn, fulham));
        }

    }

    @Test
    public void testLastGamesForTeam() throws Exception {
        Club blackburn = service.findClubByName(DbTestData.BLACKBURN_NAME);
        Game game = service.lastGamesForTeam(blackburn.getId(), 1).get(0);
        assertThat(game.getTeams(), hasItem(blackburn));

    }

    @Test
    public void testLastSeasons() throws Exception {
        List<Season> seasons = service.lastSeasons();
        assertEquals(1, seasons.size());

        newSeason(seasons.get(0).getContest());

        List<Season> newSeasons = service.lastSeasons();
        assertEquals(1, newSeasons.size());
        assertNotEquals(seasons, newSeasons);
        assertEquals(seasons.get(0).getContest(), newSeasons.get(0).getContest());
    }

    @Test
    public void testFindTeamInSeasonByTeamAndSeason() {
        assertNotNull(service.findTeamInSeasonByTeamAndSeason(
                service.findClubByName(DbTestData.BLACKBURN_NAME).getId(),
                service.lastSeasonByClubName(DbTestData.BLACKBURN_NAME).getId()));

    }

    @Test
    public void testGetCumulativeGoals() {
        service.getCumulativeGoals(Arrays.asList(service.findClubByName(DbTestData.BLACKBURN_NAME).getId()),
                service.lastSeasonByClubName(DbTestData.BLACKBURN_NAME).getId());
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
