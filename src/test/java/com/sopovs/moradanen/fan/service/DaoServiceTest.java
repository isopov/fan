package com.sopovs.moradanen.fan.service;

import com.sopovs.moradanen.fan.AbstractServiceTest;
import com.sopovs.moradanen.fan.AbstractTransactionalServiceTest;
import com.sopovs.moradanen.fan.bootstrap.DbTestData;
import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Contest;
import com.sopovs.moradanen.fan.domain.Season;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class DaoServiceTest extends AbstractTransactionalServiceTest {


    @Autowired
    private IDaoService service;

    @PersistenceContext
    private EntityManager em;


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
        newSeason.setEnd(LocalDate.now().plusYears(10));
        newSeason.setStart(LocalDate.now().plusYears(9));
        newSeason.setContest(contest);
        em.persist(newSeason);
        return newSeason;
    }
}
