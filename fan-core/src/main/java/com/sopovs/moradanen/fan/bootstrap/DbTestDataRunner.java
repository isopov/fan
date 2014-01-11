package com.sopovs.moradanen.fan.bootstrap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.sopovs.moradanen.fan.service.IDaoService;

//http://forum.springsource.org/showthread.php?36813-Transactional-annotation-in-an-InitializingBean
public class DbTestDataRunner implements InitializingBean {

    @Autowired
    private IDbTestData td;

    @Autowired(required = false)
    private FootballData fd;

    @Autowired
    private IDaoService service;

    @Autowired(required = false)
    private ITestGameWithDetailsData testGameWithDetailsData;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (notCreated()) {
            td.createTestData();
            if (testGameWithDetailsData != null) {
                testGameWithDetailsData.createTestGameWithDetails();
            }
            if (fd != null) {
                fd.importFootballData();
            }
        }
    }

    private boolean notCreated() {
        return service.findContestByName(DbTestData.BARCLAYS_PREMIER_LEAGUE) == null;
    }

}
