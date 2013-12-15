package com.sopovs.moradanen.fan;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ImportResource({ "classpath:securityContext.xml", "classpath:coreApplicationContext.xml" })
public class TestApplicationConfiguration {

    @Bean
    @Lazy(false)
    public PropertyPlaceholderConfigurer propertiesPlacholder() {
        PropertyPlaceholderConfigurer propertiesPlacholder = new PropertyPlaceholderConfigurer();
        propertiesPlacholder.setLocation(new ClassPathResource("application.properties"));
        return propertiesPlacholder;
    }
}
