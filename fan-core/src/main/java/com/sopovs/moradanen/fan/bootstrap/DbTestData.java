package com.sopovs.moradanen.fan.bootstrap;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.sopovs.moradanen.fan.domain.Contest;
import com.sopovs.moradanen.fan.domain.ContestType;
import com.sopovs.moradanen.fan.domain.Country;
import com.sopovs.moradanen.fan.domain.Lang;
import com.sopovs.moradanen.fan.domain.infra.I18nPage;
import com.sopovs.moradanen.fan.domain.infra.IndexPage;
import com.sopovs.moradanen.fan.domain.infra.User;
import com.sopovs.moradanen.fan.domain.infra.UserRole;
import com.sopovs.moradanen.fan.service.IDaoService;

@Transactional
public class DbTestData implements IDbTestData {
    public static final String BARCLAYS_PREMIER_LEAGUE = "Barclays Premier League";
    public static final String ENGLAND_LEAGUE_CHAMPIONSHIP = "Football League Championship";
    public static final String ENGLAND_LEAGUE_ONE = "Football League One";
    public static final String ENGLAND_LEAGUE_TWO = "Football League Two";
    public static final String ENGLAND_FOOTBALL_CONFERENCE = "Football Conference";
    private final Logger logger = LoggerFactory.getLogger(DbTestData.class);
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private IDaoService service;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void createTestData() {
        logger.info("Creating Test Data");
        createUsers();
        createIndexPage();

        createEnglandLeagues();
    }

    private void createIndexPage() {
        logger.info("Creating index page");
        IndexPage page = new IndexPage();
        page.setTitle("Welcome to the Football (Soccer) Analytics Portal");
        page.setShortTitle("index"); // dummy and not used
        page.setText("This site is in the early stages of development and is filled with test data.\n"
                + "Any links to teams, games and players overviews and analysis will change, so please do "
                + "not share them to avoid ugly \"Not found\" message.");
        page.setAuthor((User) userDetailsService.loadUserByUsername("isopov"));

        page.setI18ns(Collections.singletonMap(Lang.RU, new I18nPage(page, Lang.RU,
                "Добро пожаловать на портал футбольной аналитки",
                "Этот сайт находится на начальном этапе разработки и наполнен тестовыми данными.\n"
                        + "Любые ссылки на команды, игры или игроков поменяются, поэтому пожалуйста не сохраняйте их,"
                        + "и не отправляте никому, чтобы избежать гаденького \"Ресурс не найден\".")));

        em.persist(page);
    }

    private void createUsers() {

        logger.info("Creating test users");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        {
            User editor = new User();
            editor.setUsername("isopov");
            // TODO change to something meaningful when editing functions are
            // implemented
            editor.setPassword(encoder.encode("isopov"));
            editor.setRoles(Arrays.asList(new UserRole(UserRole.Role.EDITOR, editor)));

            em.persist(editor);
        }
        {
            User viewer = new User();
            viewer.setUsername("viewer");
            viewer.setPassword(encoder.encode("viewer"));
            viewer.setRoles(Arrays.asList(new UserRole(UserRole.Role.USER, viewer)));

            em.persist(viewer);
        }

    }

    private void createEnglandLeagues() {
        logger.info("Creating England Premiership");
        Country england = new Country();
        england.setCode("EN");
        england.setName("England");
        em.persist(england);

        Contest premier = new Contest();
        premier.setHolder(england);
        premier.setPosition(ContestType.FIRST);
        premier.setName(BARCLAYS_PREMIER_LEAGUE);
        em.persist(premier);
        checkNotNull(premier.getId());

        Contest champ = new Contest();
        champ.setHolder(england);
        champ.setPosition(ContestType.SECOND);
        champ.setName(ENGLAND_LEAGUE_CHAMPIONSHIP);
        em.persist(champ);
        checkNotNull(champ.getId());

        Contest one = new Contest();
        one.setHolder(england);
        one.setPosition(ContestType.SECOND);
        one.setName(ENGLAND_LEAGUE_ONE);
        em.persist(one);
        checkNotNull(one.getId());

        Contest two = new Contest();
        two.setHolder(england);
        two.setPosition(ContestType.SECOND);
        two.setName(ENGLAND_LEAGUE_TWO);
        em.persist(two);
        checkNotNull(two.getId());

        Contest conference = new Contest();
        conference.setHolder(england);
        conference.setPosition(ContestType.SECOND);
        conference.setName(ENGLAND_FOOTBALL_CONFERENCE);
        em.persist(conference);
        checkNotNull(conference.getId());

        england.setContests(Arrays.asList(premier, champ, one, two, conference));
    }

}
