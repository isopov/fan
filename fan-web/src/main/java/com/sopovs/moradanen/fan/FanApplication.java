package com.sopovs.moradanen.fan;

import com.google.common.collect.ImmutableSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

@Configuration
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class, ErrorMvcAutoConfiguration.class, FreeMarkerAutoConfiguration.class})
@Import({CoreApplicationConfiguration.class, WebApplicationConfiguration.class, SecurityConfiguration.class})
public class FanApplication implements EmbeddedServletContainerCustomizer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(FanApplication.class, args);
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		ErrorPage notFoundPage = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
		ErrorPage forbiddenPage = new ErrorPage(HttpStatus.FORBIDDEN, "/403");
		ErrorPage badRequest = new ErrorPage(HttpStatus.BAD_REQUEST, "/error");
		container.setErrorPages(ImmutableSet.of(notFoundPage, forbiddenPage, badRequest));
	}
}
