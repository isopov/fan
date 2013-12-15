package com.sopovs.moradanen.fan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpStatus;

import com.google.common.collect.ImmutableSet;

@Configuration
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
@Import({ CoreApplicationConfiguration.class, WebApplicationConfiguration.class })
@ImportResource({ "classpath:securityContext.xml" })
public class FanApplication {

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        ErrorPage notFoundPage = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        ErrorPage forbiddenPage = new ErrorPage(HttpStatus.FORBIDDEN, "/403");
        factory.setErrorPages(ImmutableSet.of(notFoundPage, forbiddenPage));
        return factory;
    }

    // This does not work
    @Bean
    public ErrorPage error404() {
        return new ErrorPage(HttpStatus.NOT_FOUND, "/404");
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FanApplication.class, args);
    }
}
