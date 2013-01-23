package com.sopovs.moradanen.fan.bootstrap;

import com.google.common.base.Preconditions;

import static com.sopovs.moradanen.fan.domain.PlayerInGamePosition.*;

import com.google.common.collect.Maps;
import com.sopovs.moradanen.fan.domain.*;
import com.sopovs.moradanen.fan.service.IDaoService;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Transactional
public class DbTestData implements IDbTestData {
    public static final String FULHAM = "Fulham";
    public static final String BLACKBURN_NAME = "Blackburn";
    public static final String BARCLAYS_PREMIER_LEAGUE = "Barclays Premier League";


    private final DateTimeFormatter df = DateTimeFormat.forPattern("dd/mm/yy");

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
            premier.setName(BARCLAYS_PREMIER_LEAGUE);


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

            importFootballData();
        }
    }

    /**
     * Importer for data from http://www.football-data.co.uk/data.php
     */
    private void importFootballData() {
        importDataFromFile("/1011-E0.csv");
        importDataFromFile("/0910-E0.csv");
    }

    private void importDataFromFile(String fileName){
        Contest premier = service.findContestByName(DbTestData.BARCLAYS_PREMIER_LEAGUE);


        FootbalDataGameHeader h = null;
        Season season = new Season();
        em.persist(season);
        try (Scanner scanner = new Scanner(DbTestData.class.getResourceAsStream(fileName))) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if (h == null) {
                    h = new FootbalDataGameHeader(s);
                    continue;
                }
                String[] gameString = s.split(",");

                String hostName = gameString[h.indexes.get("HomeTeam")];
                Club host = service.findClubByName(hostName);
                if (host == null) {
                    host = new Club(hostName);
                    em.persist(host);
                }

                String guestName = gameString[h.indexes.get("AwayTeam")];
                Club guest = service.findClubByName(guestName);
                if (guest == null) {
                    guest = new Club(guestName);
                    em.persist(guest);
                }

                Game game = new Game();

                LocalDateTime date = df.parseLocalDateTime(gameString[h.indexes.get("Date")]);
                game.setDate(date);
                game.setSeason(season);
                game.setTeams(Arrays.asList(new TeamInGame(guest, game, TeamPosition.GUEST), new TeamInGame(host, game, TeamPosition.HOST)));
                game.getGuest().setGoals(Integer.valueOf(gameString[h.indexes.get("FTAG")]));
                game.getHost().setGoals(Integer.valueOf(gameString[h.indexes.get("FTHG")]));
                em.persist(game);

            }
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

    //    Div = League Division
//    Date = Match Date (dd/mm/yy)
//    HomeTeam = Home Team
//    AwayTeam = Away Team
//    FTHG = Full Time Home Team Goals
//    FTAG = Full Time Away Team Goals
//
//    Match Statistics (where available)
//    Attendance = Crowd Attendance
//    HS = Home Team Shots
//            AS = Away Team Shots
//    HST = Home Team Shots on Target
//            AST = Away Team Shots on Target
//    HHW = Home Team Hit Woodwork
//    AHW = Away Team Hit Woodwork
//    HC = Home Team Corners
//            AC = Away Team Corners
//    HF = Home Team Fouls Committed
//    AF = Away Team Fouls Committed
//    HO = Home Team Offsides
//            AO = Away Team Offsides
//    HY = Home Team Yellow Cards
//    AY = Away Team Yellow Cards
//    HR = Home Team Red Cards
//    AR = Away Team Red Cards
    private static class FootbalDataGameHeader {

        private static List<String> values = Arrays.asList(
                "Date", "HomeTeam", "AwayTeam", "FTHG", "FTAG", "Attendance", "HS", "AS", "HST", "AST",
                "HHW", "AHW", "HC", "AC", "HF", "AF", "HO", "AO", "HY", "AY", "HR", "AR"
        );

        private Map<String, Integer> indexes = Maps.newHashMap();

        public FootbalDataGameHeader(String header) {
            List<String> headers = Arrays.asList(header.split(","));
            for (String value : values) {
                int index = headers.indexOf(value);
                if (index >= 0) {
                    indexes.put(value, index);
                }
            }
        }
    }
}
