package com.sopovs.moradanen.fan;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Import({CoreApplicationConfiguration.class, SecurityConfiguration.class})
public class TestApplicationConfiguration {

    @Bean
    @Lazy(false)
    public static PropertyPlaceholderConfigurer propertiesPlacholder() {
        PropertyPlaceholderConfigurer propertiesPlacholder = new PropertyPlaceholderConfigurer();
        propertiesPlacholder.setLocation(new ClassPathResource("application.properties"));
        return propertiesPlacholder;
    }
}
