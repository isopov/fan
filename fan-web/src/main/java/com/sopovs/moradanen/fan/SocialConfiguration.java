package com.sopovs.moradanen.fan;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jpa.JpaTemplate;
import org.springframework.social.connect.jpa.JpaUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.config.annotation.EnableFacebook;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.config.annotation.EnableTwitter;

import com.sopovs.moradanen.fan.security.SimpleSignInAdapter;

@Configuration
@EnableFacebook(appId = "${facebook.appid}", appSecret = "${facebook.secret}", appNamespace = "${facebook.namespace}")
@EnableTwitter(appId = "${twitter.appid}", appSecret = "${twitter.secret}")
public class SocialConfiguration {

    @Bean
    @Autowired
    public UsersConnectionRepository usersConnectionRepository(JpaTemplate jpaTemplate,ConnectionFactoryLocator connectionFactoryLocator) {
        return new JpaUsersConnectionRepository(jpaTemplate, connectionFactoryLocator, textEncryptor());
    }

    @Bean
    public TextEncryptor textEncryptor() {
        // TODO use encryption!
        return Encryptors.noOpText();
    }

    @Bean
    public AuthenticationNameUserIdSource userIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.NO)
    @Autowired
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }

    @Bean
    @Autowired
    public ProviderSignInController psc(ConnectionFactoryLocator connectionFactoryLocator,
            UsersConnectionRepository usersConnectionRepository, SignInAdapter signInAdapter) {
        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
    }

    @Bean
    @Autowired
    public SignInAdapter signInAdapter(RequestCache requestCache) {
        return new SimpleSignInAdapter(requestCache);
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.NO)
    @Autowired
    public ConnectionRepository connectionRepository(UsersConnectionRepository usersConnectionRepository,
            HttpServletRequest request) {
        // TODO request.userPrincipal.principal.id.toString()
        return usersConnectionRepository.createConnectionRepository(((Authentication) request.getUserPrincipal())
                .getPrincipal().toString());
    }
}
