package com.sopovs.moradanen.fan.util;

import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;

@SuppressWarnings("deprecation")
public class DbSchemaGenerator {

    public static void main(String... args) {
        System.setProperty("spring.profile.active", "postgres");
        try (ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "coreApplicationContext.xml")) {

            AbstractEntityManagerFactoryBean fb = applicationContext.getBean(AbstractEntityManagerFactoryBean.class);
            Ejb3Configuration cfg = new Ejb3Configuration();

            Ejb3Configuration configured = cfg.configure(fb.getPersistenceUnitInfo(), fb.getJpaPropertyMap());
            SchemaExport schemaExport = new SchemaExport(configured.getHibernateConfiguration());
            schemaExport.create(true, false);
        }
    }

}
