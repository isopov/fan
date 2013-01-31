package com.sopovs.moradanen.fan;

import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SuppressWarnings("deprecation")
@ActiveProfiles("postgres")
@Ignore
public class DbSchemaGenerator extends AbstractServiceTest{

	@Autowired
	private AbstractEntityManagerFactoryBean fb;

	@Test
	public void generate() {
		Ejb3Configuration cfg = new Ejb3Configuration();
		Ejb3Configuration configured = cfg.configure(fb.getPersistenceUnitInfo(), fb.getJpaPropertyMap());
		SchemaExport schemaExport = new SchemaExport(configured.getHibernateConfiguration());
		schemaExport.create(true, false);
	}

}
