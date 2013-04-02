import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sopovs.moradanen.fan.bootstrap.DbTestData;
import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.Contest;
import com.sopovs.moradanen.fan.domain.Game;
import com.sopovs.moradanen.fan.domain.Season;
import com.sopovs.moradanen.fan.domain.TeamInGame;
import com.sopovs.moradanen.fan.domain.TeamPosition;
import com.sopovs.moradanen.fan.service.IDaoService;

//TODO
//@ActiveProfiles("postgres")
//@Ignore
@Transactional
// @TransactionConfiguration(defaultRollback = false)
public class FootballDataImporter {
    private final DateTimeFormatter df = DateTimeFormat.forPattern("dd/mm/yy");
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private IDaoService service;

    // @Test
    public void importData() throws FileNotFoundException {
        for (File f : new File("/home/isopov/Downloads/football/E0").listFiles()) {
            importDataFromFile(new FileInputStream(f));
        }
    }

    private void importDataFromFile(InputStream inputStream) {
        Contest premier = service.findContestByName(DbTestData.BARCLAYS_PREMIER_LEAGUE);

        FootbalDataGameHeader h = null;
        Season season = new Season();
        season.setContest(premier);
        season.setStartDate(LocalDate.now());
        season.setEndDate(LocalDate.now());
        em.persist(season);
        try (Scanner scanner = new Scanner(inputStream)) {
            int games = 0;
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if (h == null) {
                    h = new FootbalDataGameHeader(s);
                    continue;
                }
                String[] gameString = s.split(",");
                if (h.indexes.get("HomeTeam") >= gameString.length) {
                    System.out.println("Ouch!");
                }
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
                game.setGameDate(date);
                game.setSeason(season);
                season.addGame(game);
                game.setTeamsInGame(Arrays.asList(new TeamInGame(guest, game, TeamPosition.GUEST), new TeamInGame(host,
                        game, TeamPosition.HOST)));

                game.getHost().setGoals(h.value(gameString, "FTHG"));

                game.getGuest().setGoals(h.value(gameString, "FTAG"));

                game.getHost().setShots(h.value(gameString, "HS"));

                game.getGuest().setShots(h.value(gameString, "AS"));

                game.getHost().setShotsOnTarget(h.value(gameString, "HST"));

                game.getGuest().setShotsOnTarget(h.value(gameString, "AST"));

                game.getHost().setRedCards(h.value(gameString, "HR"));

                game.getGuest().setRedCards(h.value(gameString, "AR"));

                game.getHost().setYellowCards(h.value(gameString, "HY"));

                game.getGuest().setYellowCards(h.value(gameString, "AY"));

                game.getHost().setOffsides(h.value(gameString, "HO"));

                game.getGuest().setOffsides(h.value(gameString, "AO"));

                game.getHost().setFouls(h.value(gameString, "HF"));

                game.getGuest().setFouls(h.value(gameString, "AF"));

                game.getHost().setCorners(h.value(gameString, "HC"));

                game.getGuest().setCorners(h.value(gameString, "AC"));

                em.persist(game);
                games++;
                if (games % 30 == 0) {
                    System.out.println(games + " games imported");
                }
            }
            List<LocalDateTime> dates = Lists.transform(season.getGames(), new Function<Game, LocalDateTime>() {
                @Override
                public LocalDateTime apply(Game input) {
                    return input.getGameDate();
                }
            });

            season.setEndDate(Collections.max(dates).toLocalDate());
            season.setStartDate(Collections.min(dates).toLocalDate());
            em.persist(season);
        }
    }

    // Div = League Division
    // Date = Match Date (dd/mm/yy)
    // HomeTeam = Home Team
    // AwayTeam = Away Team
    // FTHG = Full Time Home Team Goals
    // FTAG = Full Time Away Team Goals
    //
    // Match Statistics (where available)
    // Attendance = Crowd Attendance
    // HS = Home Team Shots
    // AS = Away Team Shots
    // HST = Home Team Shots on Target
    // AST = Away Team Shots on Target
    // HHW = Home Team Hit Woodwork
    // AHW = Away Team Hit Woodwork
    // HC = Home Team Corners
    // AC = Away Team Corners
    // HF = Home Team Fouls Committed
    // AF = Away Team Fouls Committed
    // HO = Home Team Offsides
    // AO = Away Team Offsides
    // HY = Home Team Yellow Cards
    // AY = Away Team Yellow Cards
    // HR = Home Team Red Cards
    // AR = Away Team Red Cards
    private static class FootbalDataGameHeader {

        private static List<String> values = Arrays.asList("Date", "HomeTeam", "AwayTeam", "FTHG", "FTAG",
                "Attendance", "HS", "AS", "HST", "AST", "HHW", "AHW", "HC", "AC", "HF", "AF", "HO", "AO", "HY", "AY",
                "HR", "AR");
        private final Map<String, Integer> indexes = Maps.newHashMap();

        public FootbalDataGameHeader(String header) {
            List<String> headers = Arrays.asList(header.split(","));
            for (String value : values) {
                int index = headers.indexOf(value);
                if (index >= 0) {
                    indexes.put(value, index);
                }
            }
        }

        public Integer value(String[] values, String header) {
            Integer index = indexes.get(header);
            if (index == null)
                return null;
            String value = values[index];
            if (Strings.isNullOrEmpty(value))
                return null;
            return Integer.valueOf(value);
        }
    }

}
