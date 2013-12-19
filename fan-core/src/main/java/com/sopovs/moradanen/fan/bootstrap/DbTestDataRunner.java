package com.sopovs.moradanen.fan.bootstrap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.sopovs.moradanen.fan.service.IDaoService;

//http://forum.springsource.org/showthread.php?36813-Transactional-annotation-in-an-InitializingBean
public class DbTestDataRunner implements InitializingBean {

    @Autowired
    private IDbTestData td;

    @Autowired(required = false)
    private IFootballData fd;

    @Autowired
    private IDaoService service;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (notCreated()) {
            td.createTestData();
            if (fd != null) {
                fd.importFootballData();
            }
        }
    }

    private boolean notCreated() {
        return service.findClubByName(DbTestData.BLACKBURN_NAME) == null;
    }

}
