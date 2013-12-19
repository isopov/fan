package com.sopovs.moradanen.fan;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.collect.ImmutableMap;
import com.sopovs.moradanen.fan.bootstrap.DbTestData;
import com.sopovs.moradanen.fan.bootstrap.DbTestDataRunner;
import com.sopovs.moradanen.fan.bootstrap.FootballData;
import com.sopovs.moradanen.fan.bootstrap.IDbTestData;
import com.sopovs.moradanen.fan.bootstrap.IFootballData;

@Configuration
@ComponentScan(basePackages = "com.sopovs.moradanen.fan.service")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.sopovs.moradanen.fan.repository")
public class CoreApplicationConfiguration {

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).build();
        return db;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPackagesToScan("com.sopovs.moradanen.fan.domain");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setDataSource(dataSource());

        entityManagerFactory.setJpaPropertyMap(ImmutableMap.<String, String> builder()
                .put("hibernate.hbm2ddl.auto", "update")
                // TODO - why not autodetection?
                .put("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .put("hibernate.format_sql", "true")
                // TODO - https://hibernate.onjira.com/browse/HHH-7457 Hibernate
                // guys strongly suggest to not use this
                .put("hibernate.enable_lazy_load_no_trans", "true")
                .build());
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean
    public IDbTestData bootstrap() {
        return new DbTestData();
    }

    @Bean
    public DbTestDataRunner bootstrapRunner() {
        return new DbTestDataRunner();
    }

    @Bean
    @Profile("football-data")
    public IFootballData footballData() {
        return new FootballData();
    }

}
