package com.sopovs.moradanen.fan.bootstrap;

import com.google.common.base.Preconditions;

import static com.sopovs.moradanen.fan.domain.PlayerInGamePosition.*;

import com.sopovs.moradanen.fan.domain.*;
import com.sopovs.moradanen.fan.service.IDaoService;
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
            Game game = new Game();
            Club roversClub = new Club(BLACKBURN_NAME);


//            POS	 No	 Name	 SH	 SG	 G	 A	 OF	 FD	 FC	 SV	 YC	 RC
//                    G
//            13	Mark Bunn	 -	 -	 -	 -	 -	 -	 -	 -	 -	 -
//                    D
//            33	Josh Morris	 -	 -	 -	 -	 -	 -	 -	 -	 -	 -
//                    D
//            39	Adam Henley	 -	 -	 -	 -	 -	 -	 -	 -	 -	 -
//                    M
//            10	Mauro Formica  	 1	 1	 1	 -	 -	 1	 3	 -	 -	 -
//                    M
//            29	Simon Vukcevic	 -	 -	 -	 -	 -	 -	 -	 -	 -	 -
//                    F
//            20	Ruben Rochina	 -	 -	 -	 -	 -	 -	 -	 -	 -	 -
//                    F
//            25	David Goodwillie

            new TeamInGame(roversClub, game
                    , new PlayerInGame(new PlayerInTeam(new Player("Paul", "Robinson"), roversClub), GOALKEEPER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Martin", "Olsson"), roversClub), DEFENDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Gael", "Givet"), roversClub), DEFENDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Grant", "Hanley"), roversClub), DEFENDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Jason", "Lowe"), roversClub), DEFENDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Morten", "Gamst", "Pedersen"), roversClub), MIDFIELDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Radosav", "Petrovic"), roversClub), MIDFIELDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("Steven", "Nzonzi"), roversClub), MIDFIELDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("David", "Hoilett"), roversClub), MIDFIELDER).addStart(0)
                    , new PlayerInGame(new PlayerInTeam(new Player("David", "Dunn"), roversClub), FORWARD).addStart(0).addEnd(69)
                    , new PlayerInGame(new PlayerInTeam(new Player("Yakubu", "Aiyegbeni"), roversClub), FORWARD).addStart(0)


                    , new PlayerInGame(new PlayerInTeam(new Player("Mark", "Bunn"), roversClub), GOALKEEPER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Josh", "Morris"), roversClub), DEFENDER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Adam", "Henley"), roversClub), DEFENDER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Mauro", "Formica"), roversClub), MIDFIELDER)
                    .addStart(69).addGoal(new Goal(79))
                    , new PlayerInGame(new PlayerInTeam(new Player("Simon", "Vukcevic"), roversClub), MIDFIELDER)
                    , new PlayerInGame(new PlayerInTeam(new Player("Ruben", "Rochina"), roversClub), FORWARD)
                    , new PlayerInGame(new PlayerInTeam(new Player("David", "Goodwillie"), roversClub), FORWARD)
            );

            Club bromwichClub = new Club(FULHAM);

            Country england = new Country();
            england.setCode("EN");
            england.setName("England");


            Contest premier = new Contest();
            england.setContests(Arrays.asList(premier));
            premier.setHolder(england);
            premier.setPositition(ContestType.FIRST);
            premier.setName("Barclays Premier League");

            em.persist(roversClub);
            em.persist(bromwichClub);
            em.persist(premier);
            em.persist(england);

            Preconditions.checkNotNull(premier.getId());
        }
    }

    private boolean notCreated() {
        return service.findClubByName(BLACKBURN_NAME) == null;
    }
}
