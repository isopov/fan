package com.sopovs.moradanen.fan;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.sopovs.moradanen.fan.bootstrap.DbTestData;
import com.sopovs.moradanen.fan.bootstrap.DbTestDataRunner;
import com.sopovs.moradanen.fan.bootstrap.FootballData;
import com.sopovs.moradanen.fan.bootstrap.FootballDataImporter;
import com.sopovs.moradanen.fan.bootstrap.IDbTestData;
import com.sopovs.moradanen.fan.bootstrap.IFootballDataImporter;
import com.sopovs.moradanen.fan.bootstrap.ITestGameWithDetailsData;
import com.sopovs.moradanen.fan.bootstrap.TestGameWithDetailsData;

@Configuration
@ComponentScan(basePackages = "com.sopovs.moradanen.fan.service")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.sopovs.moradanen.fan.repository")
public class CoreApplicationConfiguration {

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(DataSource dataSource) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource));
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    @Profile({ "default", "in-memory" })
    public DataSource inMemoryDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).build();
        return db;
    }

    @Bean
    @Profile("file-db")
    public DataSource fileDataSource(@Value("${fan.db.path:no-file}") String filePath) {
        if (Strings.isNullOrEmpty(filePath) || "no-file".equals(filePath)) {
            throw new IllegalStateException("no path specified");
        }
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:" + filePath);
        return dataSource;
    }

    @Bean
    @Autowired
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPackagesToScan("com.sopovs.moradanen.fan.domain");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setDataSource(dataSource);

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
    public FootballData footballData() {
        return new FootballData();
    }

    @Bean
    @Profile("football-data")
    public IFootballDataImporter footballDataImporter() {
        return new FootballDataImporter();
    }

    @Bean
    @Profile("default")
    public ITestGameWithDetailsData testGameWithDetailsData() {
        return new TestGameWithDetailsData();
    }

}
