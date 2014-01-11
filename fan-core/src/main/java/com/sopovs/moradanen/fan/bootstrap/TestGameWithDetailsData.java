package com.sopovs.moradanen.fan.bootstrap;

import static com.sopovs.moradanen.fan.domain.PlayerInGamePosition.DEFENDER;
import static com.sopovs.moradanen.fan.domain.PlayerInGamePosition.FORWARD;
import static com.sopovs.moradanen.fan.domain.PlayerInGamePosition.GOALKEEPER;
import static com.sopovs.moradanen.fan.domain.PlayerInGamePosition.MIDFIELDER;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Game;
import com.sopovs.moradanen.fan.domain.Goal;
import com.sopovs.moradanen.fan.domain.Player;
import com.sopovs.moradanen.fan.domain.PlayerInGame;
import com.sopovs.moradanen.fan.domain.PlayerInTeam;
import com.sopovs.moradanen.fan.domain.Season;
import com.sopovs.moradanen.fan.domain.TeamInGame;
import com.sopovs.moradanen.fan.domain.TeamInSeason;
import com.sopovs.moradanen.fan.domain.TeamPosition;
import com.sopovs.moradanen.fan.service.IDaoService;

@Transactional
public class TestGameWithDetailsData implements ITestGameWithDetailsData {

    public static final String FULHAM = "Fulham";
    public static final String BLACKBURN_NAME = "Blackburn";
    private final Logger logger = LoggerFactory.getLogger(DbTestData.class);
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private IDaoService service;

    @Override
    public void createTestGameWithDetails() {
        logger.info("Creating test game with details");

        Game game = new Game();
        game.setGameDate(LocalDateTime.now());
        Club rovers = new Club(BLACKBURN_NAME);

        Season season = new Season();
        season.setContest(service.findContestByName(DbTestData.BARCLAYS_PREMIER_LEAGUE));

        TeamInSeason roversInSeason = new TeamInSeason();
        roversInSeason.setTeam(rovers);
        roversInSeason.setSeason(season);

        TeamInGame roversTeam = new TeamInGame(
                roversInSeason,
                game,
                new PlayerInGame(new PlayerInTeam(new Player("Paul", "Robinson"), rovers), GOALKEEPER).addStart(0),
                new PlayerInGame(new PlayerInTeam(new Player("Martin", "Olsson"), rovers), DEFENDER).addStart(0),
                new PlayerInGame(new PlayerInTeam(new Player("Gael", "Givet"), rovers), DEFENDER).addStart(0),
                new PlayerInGame(new PlayerInTeam(new Player("Grant", "Hanley"), rovers), DEFENDER).addStart(0),
                new PlayerInGame(new PlayerInTeam(new Player("Jason", "Lowe"), rovers), DEFENDER).addStart(0),
                new PlayerInGame(new PlayerInTeam(new Player("Morten", "Gamst", "Pedersen"), rovers), MIDFIELDER)
                        .addStart(0).addGoal(new Goal(49)) // TODO this should
                                                           // be 45 +4
                ,
                new PlayerInGame(new PlayerInTeam(new Player("Radosav", "Petrovic"), rovers), MIDFIELDER).addStart(0),
                new PlayerInGame(new PlayerInTeam(new Player("Steven", "Nzonzi"), rovers), MIDFIELDER).addStart(0),
                new PlayerInGame(new PlayerInTeam(new Player("David", "Hoilett"), rovers), MIDFIELDER).addStart(0),
                new PlayerInGame(new PlayerInTeam(new Player("David", "Dunn"), rovers), FORWARD).addStart(0).addEnd(69)
                        .addGoal(new Goal(46)), new PlayerInGame(new PlayerInTeam(new Player("Yakubu", "Aiyegbeni"),
                        rovers), FORWARD).addStart(0).addRedCards(1).addEnd(23)

                , new PlayerInGame(new PlayerInTeam(new Player("Mark", "Bunn"), rovers), GOALKEEPER), new PlayerInGame(
                        new PlayerInTeam(new Player("Josh", "Morris"), rovers), DEFENDER), new PlayerInGame(
                        new PlayerInTeam(new Player("Adam", "Henley"), rovers), DEFENDER), new PlayerInGame(
                        new PlayerInTeam(new Player("Mauro", "Formica"), rovers), MIDFIELDER).addStart(69).addGoal(
                        new Goal(79)), new PlayerInGame(new PlayerInTeam(new Player("Simon", "Vukcevic"), rovers),
                        MIDFIELDER),
                new PlayerInGame(new PlayerInTeam(new Player("Ruben", "Rochina"), rovers), FORWARD), new PlayerInGame(
                        new PlayerInTeam(new Player("David", "Goodwillie"), rovers), FORWARD));
        roversTeam.setPosition(TeamPosition.HOST);

        fillBackLinkOnPlayers(roversTeam);

        Club fulham = new Club(FULHAM);
        TeamInSeason fulhamInSeason = new TeamInSeason();
        fulhamInSeason.setTeam(fulham);
        fulhamInSeason.setSeason(season);

        TeamInGame fulhamTeam = new TeamInGame(fulhamInSeason, game, new PlayerInGame(new PlayerInTeam(new Player(
                "David",
                "Stockdale"), fulham), GOALKEEPER).addStart(0), new PlayerInGame(new PlayerInTeam(new Player("Stephen",
                "Kelly"), fulham), DEFENDER).addStart(0), new PlayerInGame(new PlayerInTeam(new Player("John", "Arne",
                "Riise"), fulham), DEFENDER).addStart(0).addEnd(74), new PlayerInGame(new PlayerInTeam(new Player(
                "Brede", "Hangeland"), fulham), DEFENDER).addStart(0), new PlayerInGame(new PlayerInTeam(new Player(
                "Philippe", "Senderos"), fulham), DEFENDER).addStart(0).addYellowCards(1), new PlayerInGame(
                new PlayerInTeam(new Player("Danny", "Murphy"), fulham), MIDFIELDER).addStart(0), new PlayerInGame(
                new PlayerInTeam(new Player("Damien", "Duff"), fulham), MIDFIELDER).addStart(0).addGoal(new Goal(56)),
                new PlayerInGame(new PlayerInTeam(new Player("Clint", "Dempsey"), fulham), MIDFIELDER).addStart(0),
                new PlayerInGame(new PlayerInTeam(new Player("Mousa", "Dembele"), fulham), MIDFIELDER).addStart(0)
                        .addEnd(36), new PlayerInGame(new PlayerInTeam(new Player("Bryan", "Ruiz"), fulham), FORWARD)
                        .addStart(0).addEnd(69), new PlayerInGame(new PlayerInTeam(new Player("Bobby", "Zamora"),
                        fulham), FORWARD).addStart(0)

                , new PlayerInGame(new PlayerInTeam(new Player("Neil", "Etheridge"), fulham), GOALKEEPER),
                new PlayerInGame(new PlayerInTeam(new Player("Aaron", "Hughes"), fulham), DEFENDER), new PlayerInGame(
                        new PlayerInTeam(new Player("Steve", "Sidwell"), fulham), MIDFIELDER), new PlayerInGame(
                        new PlayerInTeam(new Player("Pajtim", "Kasami"), fulham), MIDFIELDER), new PlayerInGame(
                        new PlayerInTeam(new Player("Kerim", "Frei"), fulham), MIDFIELDER).addStart(36).addEnd(87),
                new PlayerInGame(new PlayerInTeam(new Player("Simon", "Davies"), fulham), MIDFIELDER).addStart(87),
                new PlayerInGame(new PlayerInTeam(new Player("Andrew", "Johnson"), fulham), FORWARD).addStart(74));
        fulhamTeam.setPosition(TeamPosition.GUEST);
        fillBackLinkOnPlayers(fulhamTeam);

        game.setTeamsInGame(Arrays.asList(roversTeam, fulhamTeam));

        season.setGames(Arrays.asList(game));
        game.setSeason(season);
        season.setStartDate(LocalDate.now().minusMonths(6));
        season.setEndDate(LocalDate.now().plusMonths(6));

        em.persist(game);
        em.persist(rovers);
        em.persist(fulham);
        em.persist(season);
        em.persist(roversInSeason);
        em.persist(fulhamInSeason);

        Preconditions.checkNotNull(fulhamTeam.getId());
        Preconditions.checkNotNull(fulham.getId());
        Preconditions.checkNotNull(roversTeam.getId());
        Preconditions.checkNotNull(rovers.getId());
        Preconditions.checkNotNull(season.getId());

        Preconditions.checkNotNull(game.getId());
        checkPLayers(fulhamTeam);
        checkPLayers(roversTeam);
    }

    private static void fillBackLinkOnPlayers(TeamInGame teamInGame) {
        int goals = 0;
        int shots = 0;
        int shotsOnTarget = 0;
        int passes = 0;
        int failedPasses = 0;
        int fouls = 0;
        int yellows = 0;
        int reds = 0;

        for (PlayerInGame p : teamInGame.getPlayers()) {
            p.setTeamInGame(teamInGame);
            if (p.getGoals() != null) {
                goals += p.getGoals().size();
            }
            if (p.getShots() != null) {
                shots += p.getShots();
            }
            if (p.getShotsOnTarget() != null) {
                shotsOnTarget += p.getShotsOnTarget();
            }
            if (p.getPasses() != null) {
                passes += p.getPasses();
            }
            if (p.getFaildPasses() != null) {
                passes += p.getFaildPasses();
            }
            if (p.getFouls() != null) {
                fouls += p.getFouls();
            }
            if (p.getYellowCards() != null) {
                yellows += p.getYellowCards();
            }
            if (p.getRedCards() != null) {
                reds += p.getRedCards();
            }

        }

        teamInGame.setGoals(goals);
        teamInGame.setShots(shots);
        teamInGame.setShotsOnTarget(shotsOnTarget);
        teamInGame.setPasses(passes);
        teamInGame.setFailedPasses(failedPasses);
        teamInGame.setFouls(fouls);
        teamInGame.setYellowCards(yellows);
        teamInGame.setRedCards(reds);

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
