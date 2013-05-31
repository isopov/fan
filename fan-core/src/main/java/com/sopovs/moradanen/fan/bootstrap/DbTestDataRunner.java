package com.sopovs.moradanen.fan.bootstrap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

//http://forum.springsource.org/showthread.php?36813-Transactional-annotation-in-an-InitializingBean
public class DbTestDataRunner implements InitializingBean {

    @Autowired
    private IDbTestData td;

    @Override
    public void afterPropertiesSet() throws Exception {
        td.createTestData();
    }
}
