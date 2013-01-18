package com.sopovs.moradanen.fan.bootstrap;

import com.google.common.base.Preconditions;

import static com.sopovs.moradanen.fan.domain.PlayerInGamePosition.*;

import com.sopovs.moradanen.fan.domain.*;
import com.sopovs.moradanen.fan.service.IDaoService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

@Transactional
public class DbTestData implements IDbTestData {

    public static final String FULHAM = "Fulham";
    public static final String BLACKBURN_NAME = "Blackburn Rovers";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IDaoService service;

    @Override
    public void createTestData() {
        if (notCreated()) {
            Country england = new Country();
            england.setCode("EN");
            england.setName("England");


            Contest premier = new Contest();
            england.setContests(Arrays.asList(premier));
            premier.setHolder(england);
            premier.setPosition(ContestType.FIRST);
            premier.setName("Barclays Premier League");


            Game game = new Game();
            Club rovers = new Club(BLACKBURN_NAME);


            TeamInGame roversTeam = new TeamInGame(rovers, game
                    , new PlayerInGame(new PlayerInTeam(new Player("Paul", "Robinson"), rovers), GOALKEEPER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Martin", "Olsson"), rovers), DEFENDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Gael", "Givet"), rovers), DEFENDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Grant", "Hanley"), rovers), DEFENDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Jason", "Lowe"), rovers), DEFENDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Morten", "Gamst", "Pedersen"), rovers), MIDFIELDER).addStart(0).addGoal(new Goal(49)) //TODO this should be 45 +4
                    , new PlayerInGame(new PlayerInTeam(new Player("Radosav", "Petrovic"), rovers), MIDFIELDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Steven", "Nzonzi"), rovers), MIDFIELDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("David", "Hoilett"), rovers), MIDFIELDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("David", "Dunn"), rovers), FORWARD).addStart(0).addEnd(69).addGoal(new Goal(46))
                    , new PlayerInGame(new PlayerInTeam(new Player("Yakubu", "Aiyegbeni"), rovers), FORWARD).addStart(0).addRedCards(1).addEnd(23)


                    , new PlayerInGame(new PlayerInTeam(new Player("Mark", "Bunn"), rovers), GOALKEEPER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Josh", "Morris"), rovers), DEFENDER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Adam", "Henley"), rovers), DEFENDER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Mauro", "Formica"), rovers), MIDFIELDER)
                    .addStart(69).addGoal(new Goal(79))
                    , new PlayerInGame(new PlayerInTeam(new Player("Simon", "Vukcevic"), rovers), MIDFIELDER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Ruben", "Rochina"), rovers), FORWARD)
                    , new PlayerInGame(new PlayerInTeam(new Player("David", "Goodwillie"), rovers), FORWARD)
            );

            fillBackLinkOnPlayers(roversTeam);


            Club fulham = new Club(FULHAM);

            TeamInGame fulhamTeam = new TeamInGame(fulham, game
                    , new PlayerInGame(new PlayerInTeam(new Player("David", "Stockdale"), fulham), GOALKEEPER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Stephen", "Kelly"), fulham), DEFENDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("John", "Arne", "Riise"), fulham), DEFENDER).addStart(0).addEnd(74)
                    , new PlayerInGame(new PlayerInTeam(new Player("Brede", "Hangeland"), fulham), DEFENDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Philippe", "Senderos"), fulham), DEFENDER).addStart(0).addYellowCards(1)
                    , new PlayerInGame(new PlayerInTeam(new Player("Danny", "Murphy"), fulham), MIDFIELDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Damien", "Duff"), fulham), MIDFIELDER).addStart(0).addGoal(new Goal(56))
                    , new PlayerInGame(new PlayerInTeam(new Player("Clint", "Dempsey"), fulham), MIDFIELDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Mousa", "Dembele"), fulham), MIDFIELDER).addStart(0).addEnd(36)
                    , new PlayerInGame(new PlayerInTeam(new Player("Bryan", "Ruiz"), fulham), FORWARD).addStart(0).addEnd(69)
                    , new PlayerInGame(new PlayerInTeam(new Player("Bobby", "Zamora"), fulham), FORWARD).addStart(0)


                    , new PlayerInGame(new PlayerInTeam(new Player("Neil", "Etheridge"), fulham), GOALKEEPER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Aaron", "Hughes"), fulham), DEFENDER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Steve", "Sidwell"), fulham), MIDFIELDER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Pajtim", "Kasami"), fulham), MIDFIELDER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Kerim", "Frei"), fulham), MIDFIELDER).addStart(36).addEnd(87)
                    , new PlayerInGame(new PlayerInTeam(new Player("Simon", "Davies"), fulham), MIDFIELDER).addStart(87)
                    , new PlayerInGame(new PlayerInTeam(new Player("Andrew", "Johnson"), fulham), FORWARD).addStart(74)
            );
            fillBackLinkOnPlayers(fulhamTeam);

            game.setTeams(Arrays.asList(roversTeam, fulhamTeam));


            Season season = new Season();
            season.setContest(premier);
            season.setGames(Arrays.asList(game));
            game.setSeason(season);
            season.setStart(LocalDate.now().minusMonths(6));
            season.setEnd(LocalDate.now().plusMonths(6));

            em.persist(game);
            em.persist(premier);
            em.persist(rovers);
            em.persist(fulham);
            em.persist(season);

            em.persist(england);

            Preconditions.checkNotNull(premier.getId());
            Preconditions.checkNotNull(fulhamTeam.getId());
            Preconditions.checkNotNull(fulham.getId());
            Preconditions.checkNotNull(roversTeam.getId());
            Preconditions.checkNotNull(rovers.getId());
            Preconditions.checkNotNull(season.getId());

            Preconditions.checkNotNull(game.getId());
            checkPLayers(fulhamTeam);
            checkPLayers(roversTeam);
        }
    }

    private boolean notCreated() {
        return service.findClubByName(BLACKBURN_NAME) == null;
    }

    private static void fillBackLinkOnPlayers(TeamInGame teamInGame) {
        for (PlayerInGame p : teamInGame.getPlayers()) {
            p.setTeamInGame(teamInGame);
        }
    }


    private static void checkPLayers(TeamInGame teamInGame) {
        for (PlayerInGame p : teamInGame.getPlayers()) {
            Preconditions.checkNotNull(p.getId());
            if (p.getGoals() != null) {
                for (Goal g : p.getGoals()) {
                    Preconditions.checkNotNull(g.getId());
                }
            }
            Preconditions.checkNotNull(p.getPlayerInTeam().getId());
            Preconditions.checkNotNull(p.getPlayerInTeam().getPlayer().getId());
            Preconditions.checkNotNull(p.getPlayerInTeam().getTeam().getId());
            Preconditions.checkNotNull(p.getTeamInGame().getId());
        }

    }
}
