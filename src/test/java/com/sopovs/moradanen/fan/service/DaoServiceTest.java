package com.sopovs.moradanen.fan.service;

import com.sopovs.moradanen.fan.AbstractServiceTest;
import com.sopovs.moradanen.fan.bootstrap.DbTestData;
import com.sopovs.moradanen.fan.domain.Season;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DaoServiceTest extends AbstractServiceTest {


    @Autowired
    private IDaoService service;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testFindClubByName() throws Exception {
        service.findClubByName(DbTestData.BLACKBURN_NAME);

    }

    @Test
    public void testListAllClubs() throws Exception {

    }

    @Test
    public void testLastSeasons() throws Exception {
        List<Season> seasons = service.lastSeasons();
        assertEquals(1, seasons.size());

        Season newSeason = new Season();
        newSeason.setEnd(LocalDate.now().plusYears(10));
        newSeason.setContest(seasons.get(0).getContest());
        em.persist(newSeason);

        List<Season> newSeasons = service.lastSeasons();
        assertEquals(1, newSeasons.size());
        assertNotEquals(seasons,newSeasons);
        assertEquals(seasons.get(0).getContest(),newSeasons.get(0).getContest());
    }
}
