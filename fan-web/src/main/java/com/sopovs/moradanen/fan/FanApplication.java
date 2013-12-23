package com.sopovs.moradanen.fan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

import com.google.common.collect.ImmutableSet;
import com.sopovs.moradanen.spring.boot.autoconfigure.web.UndertowEmbeddedServletContainerFactory;

@Configuration
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
@Import({ CoreApplicationConfiguration.class, WebApplicationConfiguration.class, SecurityConfiguration.class })
public class FanApplication {

    @Bean
    public UndertowEmbeddedServletContainerFactory undertowFactory() {

        UndertowEmbeddedServletContainerFactory factory = new
                UndertowEmbeddedServletContainerFactory();
        ErrorPage notFoundPage = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        ErrorPage forbiddenPage = new ErrorPage(HttpStatus.FORBIDDEN, "/403");
        ErrorPage badRequest = new ErrorPage(HttpStatus.BAD_REQUEST, "/error");
        factory.setErrorPages(ImmutableSet.of(notFoundPage, forbiddenPage, badRequest));
        return factory;
    }

    // This does not work
    // @Bean
    // public ErrorPage error404() {
    // return new ErrorPage(HttpStatus.NOT_FOUND, "/404");
    // }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FanApplication.class, args);
    }
}
