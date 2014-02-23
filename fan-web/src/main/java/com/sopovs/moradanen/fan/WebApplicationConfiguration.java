package com.sopovs.moradanen.fan;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.CodeSource;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.sopovs.moradanen.fan.domain.infra.QTag;
import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.taglibs.TagLibConfig;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.ServletContext;

@Configuration
@EnableWebMvc
@ComponentScan("com.sopovs.moradanen.fan.controller")
@Import(SocialConfiguration.class)
public class WebApplicationConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/WEB-INF/favicon.ico");
        registry.addResourceHandler("/robots.txt").addResourceLocations("/WEB-INF/robots.txt");
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/img/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    @Bean
    @Autowired
    public freemarker.template.Configuration freeMarkerConfig(ServletContext servletContext) throws IOException,
            TemplateException {
        FreeMarkerConfigurer freemarkerConfig = configFreeMarkerConfigurer(servletContext);
        return freemarkerConfig.getConfiguration();
    }

    @Bean
    @Autowired
    public TaglibFactory taglibFactory(ServletContext servletContext) throws IOException, TemplateException {
        FreeMarkerConfigurer freemarkerConfig = configFreeMarkerConfigurer(servletContext);
        return freemarkerConfig.getTaglibFactory();
    }

    @Autowired
    @Bean
    public FreeMarkerConfig springFreeMarkerConfig(ServletContext servletContext) throws IOException, TemplateException {
        return new MyFreeMarkerConfig(freeMarkerConfig(servletContext), taglibFactory(servletContext));
    }

    private static FreeMarkerConfigurer configFreeMarkerConfigurer(ServletContext servletContext) throws IOException,
            TemplateException {
        FreeMarkerConfigurer freemarkerConfig = new FreeMarkerConfigurer();
        freemarkerConfig
                .setPreTemplateLoaders(new ClassTemplateLoader(WebApplicationConfiguration.class, "/templates/"));
        ServletContext servletContextProxy = (ServletContext) Proxy.newProxyInstance(
                ServletContextResourceHandler.class.getClassLoader(),
                new Class<?>[] { ServletContext.class },
                new ServletContextResourceHandler(servletContext));
        freemarkerConfig.setServletContext(servletContextProxy);
        Properties settings = new Properties();
        settings.put("default_encoding", "UTF-8");
        freemarkerConfig.setFreemarkerSettings(settings);
        freemarkerConfig.afterPropertiesSet();
        return freemarkerConfig;
    }

    @Bean
    public FreeMarkerViewResolver viewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setCache(false);
        viewResolver.setSuffix(".ftl");
        viewResolver.setContentType("text/html;charset=UTF-8");
        return viewResolver;
    }

    @Bean
    public FanExceptionHandler exceptionHandler() {
        return new FanExceptionHandler();
    }

    private static class ServletContextResourceHandler implements InvocationHandler
    {

        private final ServletContext target;

        private ServletContextResourceHandler(ServletContext target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("getResourceAsStream".equals(method.getName())) {
                Object result = method.invoke(target, args);
                if (result == null) {
                    result = WebApplicationConfiguration.class.getResourceAsStream((String) args[0]);
                }
                return result;
            } else if ("getResource".equals(method.getName())) {
                Object result = method.invoke(target, args);
                if (result == null) {
                    result = WebApplicationConfiguration.class.getResource((String) args[0]);
                }
                return result;
            }

            return method.invoke(target, args);
        }
    }

    private static class MyFreeMarkerConfig implements FreeMarkerConfig {

        private final freemarker.template.Configuration configuration;
        private final TaglibFactory taglibFactory;

        private MyFreeMarkerConfig(freemarker.template.Configuration configuration, TaglibFactory taglibFactory) {
            this.configuration = configuration;
            this.taglibFactory = taglibFactory;
        }

        @Override
        public freemarker.template.Configuration getConfiguration() {
            return configuration;
        }

        @Override
        public TaglibFactory getTaglibFactory() {
            return taglibFactory;
        }
    }
}
